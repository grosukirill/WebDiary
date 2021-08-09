package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> findAllQuestions();

    QuestionDto findQuestionById(Long questionId, String token);

    List<QuestionDto> findAllQuestionsByCategory(String category);

    QuestionDto createQuestion(CreateQuestionRequest request);
}
