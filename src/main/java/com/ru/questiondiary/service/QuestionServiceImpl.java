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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable page = PageRequest.of(pageNumber, 20, Sort.by("creation_date").descending());
        Page<Question> questions;
        switch (type) {
            case "Admin":
                questions = questionRepository.findAllByIsAdminsTrue(page);
                break;
            case "Users":
                questions = questionRepository.findAllByIsAdminsFalse(user.getId(), page);
                break;
            case "Recommendations":
                questions = questionRepository.findRecommendations(user.getId(), page);
                break;
            default:
                throw new WrongFeedTypeException(String.format("Feed type %s does not match any known types.", type));
        }
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
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
    public List<QuestionDto> createRecommendations(String rawToken) {
        User user = getUserFromToken(rawToken);
        List<Vote> votes = voteRepository.findAll();
        List<RecommendedItem> recommendations = recommendationService.findRecommendations(user, votes);
        List<QuestionDto> questionDtos = new ArrayList<>();
        if (recommendations == null || recommendations.isEmpty()) {
            questionDtos = findTopTen(rawToken);
        } else {
            Optional<Question> question = questionRepository.findById(recommendations.get(0).getItemID());
            if (question.isEmpty()) {
                throw new QuestionNotFoundException(String.format("Questions with ID: [%s] not found", recommendations.get(0).getItemID()));
            }
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
