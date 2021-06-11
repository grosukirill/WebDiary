package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;


    @Override
    @Transactional
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions) {
            questionDtos.add(QuestionDto.from(question));
        }
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    @Override
    public QuestionDto getQuestionById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
        }
        return QuestionDto.from(question.get());
    }
}
