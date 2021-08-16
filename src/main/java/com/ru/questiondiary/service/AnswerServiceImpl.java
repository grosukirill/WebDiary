package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.AnswerRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.AnswerDto;
import com.ru.questiondiary.web.dto.request.CreateAnswerRequest;
import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public AnswerDto createAnswer(CreateAnswerRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        String answer = request.getAnswer();
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        String date = LocalDate.now(ZoneId.of("UTC+3")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", request.getQuestionId()));
        }
        Answer createdAnswer = answerRepository.save(Answer.builder()
                .answer(answer)
                .question(question.get())
                .user(user)
                .date(date)
                .build());
        return AnswerDto.from(createdAnswer);
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
