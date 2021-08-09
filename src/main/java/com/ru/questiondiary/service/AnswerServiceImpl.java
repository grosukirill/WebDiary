package com.ru.questiondiary.service;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public AnswerDto createAnswer(CreateAnswerRequest request) {
        String answer = request.getAnswer();
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        Optional<User> user = userRepository.findById(request.getUserId());
        LocalDate date = LocalDate.now(ZoneId.of("UTC+3"));
        Answer answerRecord = answerRepository.save(Answer.builder()
                .answer(answer)
                .question(question.get())
                .user(user.get())
                .date(date)
                .build());
        return AnswerDto.from(answerRecord);
    }
}
