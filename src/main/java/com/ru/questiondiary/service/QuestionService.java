package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> findAllQuestions();

    QuestionDto findQuestionById(Long questionId, Long userId);

    List<QuestionDto> findAllQuestionsByCategory(String category);
}
