package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.*;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Category;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final QuestionRepository questionRepository;
    private final VoteRepository voteRepository;
    private final FavoriteRepository favoriteRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public PaginationDto getStatisticsByViews(String rawToken, Integer pageNumber) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 5, Sort.by(Sort.Order.desc("views")));
        Page<Question> questionPage = questionRepository.findAll(page);
        List<QuestionDto> questionList = buildDtoFromDomain(user, questionPage);
        return new PaginationDto(questionList, questionPage.hasNext(), questionPage.getNumber()+1);
    }

    @Override
    public PaginationDto getStatisticsByAnswers(String rawToken, Integer pageNumber) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 5);
        Page<Map<String, BigInteger>> mostAnsweredQuestions = answerRepository.getMostAnsweredQuestions(user.getId(), page);
        List<Question> questions = new ArrayList<>();
        mostAnsweredQuestions.getContent().forEach((item) -> {
            Long questionId = item.get("question_id").longValue();
            Optional<Question> question = questionRepository.findById(questionId);
            if (question.isEmpty()) {
                throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
            }
            questions.add(question.get());
        });
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, questions);
        return new PaginationDto(questionDtos, mostAnsweredQuestions.hasNext(), mostAnsweredQuestions.getNumber()+1);
    }

    @Override
    @Transactional
    public PaginationDto getStatisticsByCategories(String rawToken, Integer pageNumber) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 5);
        Page<Answer> answers = answerRepository.findAllByUser(page, user);
        List<String> categories = new ArrayList<>();
        answers.getContent().forEach((answer) -> {
            Set<Category> questionCategories = answer.getQuestion().getCategories();
            questionCategories.forEach((categoryObject) -> categories.add(categoryObject.getCategory()));
        });
        Map<String, Integer> percentage = new HashMap<>();
        categories.forEach((category) -> {
            Integer value = percentage.get(category);
            percentage.put(category, (value == null) ? 1 : value + 1);
        });
        for (Map.Entry<String, Integer> entry: percentage.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Integer percent = value * 100 / categories.size();
            percentage.put(key, percent);
        }
        return new PaginationDto(percentage, answers.hasNext(), answers.getNumber()+1);
    }

    @Override
    public PaginationDto getStatisticsByVotes(String rawToken, Integer pageNumber) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 5);
        Page<Question> questions = questionRepository.findAllByCreator(page, user);
        List<Question> sorted = questions.getContent().stream().sorted(Comparator.comparing(Question::getCountOfVotes, Comparator.reverseOrder())).collect(Collectors.toList());
        List<QuestionDto> questionDtos = buildDtoFromDomain(user, sorted);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
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

    private List<QuestionDto> buildDtoFromDomain(User user, List<Question> questions) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVoteAndUser(1, user.getId());
        List<Long> downVoted = voteRepository.getAllByVoteAndUser(-1, user.getId());
        for (Question question: questions) {
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

    private User getUserFromToken(String rawToken) {
        Map<String, String> userData = tokenService.getUserDataFromToken(rawToken);
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userData.get("id")));
        }
        return user.get();
    }
}
