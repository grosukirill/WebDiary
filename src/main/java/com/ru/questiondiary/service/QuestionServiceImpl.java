package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.*;
import com.ru.questiondiary.repo.*;
import com.ru.questiondiary.web.dto.CategoryDto;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.dto.request.QuestionByDateRequest;
import com.ru.questiondiary.web.dto.request.UpdateQuestionRequest;
import com.ru.questiondiary.web.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final FavoriteRepository favoriteRepository;
    private final CategoryRepository categoryRepository;
    private final RecommendationService recommendationService;


    @Override
    @Transactional
    public PaginationDto findAllQuestions(Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20);
        Page<Question> questions = questionRepository.findAll(page);
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        Collections.shuffle(questionDtos);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
    }

    @Override
    @Transactional
    public QuestionDto findQuestionById(Long questionId, String token) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
        }
        User user = getUserFromToken(token);
        List<Answer> answers = answerRepository.getAllByQuestionAndUser(question.get(), user);
        Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question.get(), user);
        question.get().setViews(question.get().getViews() + 1);
        questionRepository.save(question.get());
        return QuestionDto.fromWithAnswers(question.get(), answers, isFavorite);
    }

    @Override
    @Transactional
    public List<QuestionDto> findAllQuestionsByCategory(Long category, String rawToken, Integer pageNumber) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20);
        Page<Question> questions = questionRepository.findAllByCategories(category, page);
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    @Override
    @Transactional
    public QuestionDto createQuestion(CreateQuestionRequest request, String rawToken) {
        Optional<Question> existingQuestion = questionRepository.findByQuestion(request.getQuestion());
        if (existingQuestion.isPresent()) {
            throw new DuplicateQuestionException("This question already exists");
        }
        User user = getUserFromToken(rawToken);
        LocalDate now = LocalDate.now(ZoneId.of("UTC+3"));
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Category with ID [%s] not found", request.getCategoryId()));
        }
        Set<Category> categories = new HashSet<>();
        categories.add(category.get());
        Question question = new Question();
        question.setQuestion(request.getQuestion());
        question.setCreator(user);
        question.setCreationDate(now);
        question.setAnswers(new ArrayList<>());
        question.setCategories(categories);
        question.setComments(new ArrayList<>());
        question.setVotes(new ArrayList<>());
        questionRepository.save(question);
        return QuestionDto.from(question, null, null);
    }

    @Override
    @Transactional
    public QuestionDto updateQuestion(Long questionId, UpdateQuestionRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(questionId, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionUpdateException("You are trying to update other people's question");
        }
        question.get().setQuestion(request.getNewQuestion());
        questionRepository.save(question.get());
        Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question.get(), user);
        return QuestionDto.from(question.get(), null, isFavorite);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id, String rawToken) {
        getUserFromToken(rawToken);
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(id, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionDeleteException("You are trying to delete other people's question");
        }
        answerRepository.deleteAllByQuestion(question.get());
        voteRepository.deleteAllByQuestion(question.get());
        questionRepository.delete(question.get());
    }

    @Override
    @Transactional
    public PaginationDto findFeed(String type, Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        List<Vote> votes = voteRepository.findAll();
        List<RecommendedItem> recommendations;
        Page<Question> questions;
        List<QuestionDto> questionDtos = new ArrayList<>();
        switch (type) {
            case "Main":
                Pageable pageForMain = PageRequest.of(pageNumber, 20, Sort.by("creation_date").descending());
                questions = questionRepository.findAllByIsAdminsTrue(pageForMain);
                questionDtos = buildDtoFromDomain(user, questions);
                return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber() + 1);
            case "New":
                List<Question> questionsList;
                if (pageNumber == 0) {
                    questionsList = questionRepository.findTopByVotesAndCreationDateLastWeek(pageNumber);
                } else {
                    int top = pageNumber * 20;
                    questionsList = questionRepository.findTopByVotesAndCreationDateLastWeek(top);
                }
                Collections.shuffle(questionsList);
                for (Question question: questionsList) {
                    questionDtos.add(QuestionDto.from(question, null, null));
                }
                return new PaginationDto(questionDtos, true, pageNumber + 1);
            case "Home":
                Pageable pageForFollowers = PageRequest.of(pageNumber, 15);
                questions = questionRepository.findFollowersFeed(user.getId(), pageForFollowers);
                for (Question question: questions.getContent()) {
                    questionDtos.add(QuestionDto.from(question, null, null));
                }
                recommendations = recommendationService.findRecommendations(user, votes, 20);
                if (recommendations == null || recommendations.size() < 15) {
                    Pageable pageable;
                    if (recommendations != null) {
                        pageable = PageRequest.of(pageNumber, 20 - recommendations.size());
                    } else  {
                        pageable = PageRequest.of(pageNumber, 20);
                    }
                    Page<Question> questionPage = questionRepository.findAll(pageable);
                    List<Question> questionList = new ArrayList<>();
                    if (recommendations != null) {
                        for (int i = 0; i <= recommendations.size()-1; i++) {
                            Optional<Question> question = questionRepository.findById(recommendations.get(i).getItemID());
                            if (question.isEmpty()) {
                                throw new QuestionNotFoundException(String.format("Questions with ID: [%s] not found", recommendations.get(i).getItemID()));
                            }
                            question.get().setViews(question.get().getViews() + 1);
                            questionRepository.save(question.get());
                            questionList.add(question.get());
                        }
                    }
                    for (int i = 0; i <= questionPage.getContent().size()-1; i++) {
                        questionList.add(questionPage.getContent().get(i));
                    }
                    List<QuestionDto> questionDtoList = new ArrayList<>();
                    for (Question question : questionList) {
                        questionDtoList.add(QuestionDto.from(question, null, null));
                    }
                    return new PaginationDto(questionDtoList, questionPage.hasNext(), questionPage.getNumber()+1);
                }
                else {
                    for (RecommendedItem item: recommendations) {
                        Optional<Question> question = questionRepository.findById(item.getItemID());
                        if (question.isEmpty()) {
                            throw new QuestionNotFoundException(String.format("Questions with ID: [%s] not found", item.getItemID()));
                        }
                        question.get().setViews(question.get().getViews() + 1);
                        questionRepository.save(question.get());
                        questionDtos.add(QuestionDto.from(question.get(), null, null));
                    }
                    Pageable pageForAnsweredAndAdmins = PageRequest.of(pageNumber, 5);
                    Page<Answer> answered = answerRepository.findAllByUser(pageForAnsweredAndAdmins, user);
                    for (Answer answer: answered) {
                        questionDtos.add(QuestionDto.from(answer.getQuestion(), null, null));
                    }
                    Page<Question> adminsQuestions = questionRepository.findAllByIsAdminsTrue(pageForAnsweredAndAdmins);
                    for (Question question: adminsQuestions) {
                        questionDtos.add(QuestionDto.from(question, null, null));
                    }
                    return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber() + 1);
                }
            case "Recommendations":
                recommendations = recommendationService.findRecommendations(user, votes, 1);
                if (recommendations == null || recommendations.size() == 0) {
                    List<Question> questionList = (List<Question>) questionRepository.findAll();
                    List<Question> sorted = questionList.stream().sorted(Comparator.comparing(Question::getCountOfVotes, Comparator.reverseOrder())).collect(Collectors.toList());
                    questionDtos = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        questionDtos.add(QuestionDto.from(sorted.get(i), null, null));
                    }
                    return new PaginationDto(questionDtos, false, null);
                } else {
                    QuestionDto question = new QuestionDto();
                    for (RecommendedItem item : recommendations) {
                        Optional<Question> questionFound = questionRepository.findById(item.getItemID());
                        if (questionFound.isEmpty()) {
                            throw new QuestionNotFoundException(String.format("Questions with ID: [%s] not found", item.getItemID()));
                        }
                        questionFound.get().setViews(questionFound.get().getViews() + 1);
                        questionRepository.save(questionFound.get());
                        question = QuestionDto.from(questionFound.get(), null, null);
                    }
                    return new PaginationDto(question, false, null);
                }
            default:
                throw new WrongFeedTypeException(String.format("Feed type %s does not match any known types.", type));
        }
    }

    @Override
    @Transactional
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: categories) {
            categoryDtos.add(CategoryDto.from(category));
        }
        return categoryDtos;
    }

    @Override
    @Transactional
    public PaginationDto findAllQuestionsByAnswerDate(Integer page, QuestionByDateRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable pageable = PageRequest.of(page, 20);
        Page<Question> questions = questionRepository.findAllByAnswerDate(user.getId(), request.getDate(), pageable);
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions.getContent()) {
            List<Answer> answers = answerRepository.getAllByQuestionAndUser(question, user);
            questionDtos.add(QuestionDto.fromWithAnswers(question, answers, null));
        }
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
    }

    @Override
    @Transactional
    public PaginationDto findAllFavoriteQuestions(Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20);
        Page<Question> questions = questionRepository.findAllFavorite(user.getId(), page);
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
    }

    @Override
    @Transactional
    public PaginationDto findLatestQuestions(Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20, Sort.by("creation_date").descending());
        Page<Question> questions = questionRepository.findLatest(page);
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
    }

    @Override
    public List<QuestionDto> searchQuestions(String pattern, String rawToken) {
        getUserFromToken(rawToken);
        List<Question> questions = questionRepository.findAllByQuestionContainingIgnoreCase(pattern);
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions) {
            questionDtos.add(QuestionDto.from(question, null, null));
        }
        return questionDtos;
    }

    @Override
    @Transactional
    public List<QuestionDto> createFirstRecommendations(String rawToken) {
        User user = getUserFromToken(rawToken);
        List<Vote> votes = voteRepository.findAll();
        List<RecommendedItem> recommendations = recommendationService.findRecommendations(user, votes, 1);
        List<QuestionDto> questionDtos = new ArrayList<>();
        if (recommendations == null || recommendations.isEmpty()) {
            questionDtos = findTopTen(rawToken);
        } else {
            Optional<Question> question = questionRepository.findById(recommendations.get(0).getItemID());
            if (question.isEmpty()) {
                throw new QuestionNotFoundException(String.format("Questions with ID: [%s] not found", recommendations.get(0).getItemID()));
            }
            question.get().setViews(question.get().getViews());
            questionRepository.save(question.get());
            questionDtos.add(QuestionDto.from(question.get(), null, null));
        }
        return questionDtos;
    }

    @Override
    @Transactional
    public List<QuestionDto> findTopTen(String rawToken) {
        getUserFromToken(rawToken);
        List<Question> questions = (List<Question>) questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> sorted = questions.stream().sorted(Comparator.comparing(Question::getCountOfVotes, Comparator.reverseOrder())).collect(Collectors.toList());
        for (int i = 0; i < 10; i++) {
            Question question = sorted.get(i);
            questionDtos.add(QuestionDto.from(question, null, null));
        }
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    private User getUserFromToken(String rawToken) {
        Map<String, String> userData = tokenService.getUserDataFromToken(rawToken);
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userData.get("id")));
        }
        return user.get();
    }

    private List<QuestionDto> buildDtoFromDomain(User user, Page<Question> questions) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVoteAndUser(1, user.getId());
        List<Long> downVoted = voteRepository.getAllByVoteAndUser(-1, user.getId());
        for (Question question: questions.getContent()) {
            Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question, user);
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true, isFavorite));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false, isFavorite));
            } else {
                questionDtos.add(QuestionDto.from(question, null, isFavorite));
            }
        }
        return questionDtos;
    }
}
