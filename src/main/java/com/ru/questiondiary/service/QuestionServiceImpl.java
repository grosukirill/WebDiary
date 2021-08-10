package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.repo.AnswerRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public List<QuestionDto> findAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions) {
            questionDtos.add(QuestionDto.from(question));
        }
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    @Override
    @Transactional
    public QuestionDto findQuestionById(Long questionId, String token) {
        Optional<Question> question = questionRepository.findById(questionId);
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
        }
        User user = userRepository.getById(Long.parseLong(userData.get("id")));
        List<Answer> answers = answerRepository.getAllByQuestionAndUser(question.get(), user);
        return QuestionDto.fromWithAnswers(question.get(), answers);
    }

    @Override
    @Transactional
    public List<QuestionDto> findAllQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.getAllByCategories(category);
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions) {
            questionDtos.add(QuestionDto.from(question));
        }
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    @Override
    @Transactional
    public QuestionDto createQuestion(CreateQuestionRequest request) {
        Map<String, String> userData = tokenService.getUserDataFromToken(request.getToken());
        User user = userRepository.getById(Long.parseLong(userData.get("id")));
        LocalDate now = LocalDate.now(ZoneId.of("UTC+3"));
        Question question = new Question();
        question.setQuestion(request.getQuestion());
        question.setCreator(user);
        question.setCreationDate(now);
        questionRepository.save(question);
        return QuestionDto.from(question);
    }
}
