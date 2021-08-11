package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.dto.request.UpdateQuestionRequest;

import java.util.List;

public interface QuestionService {
    PaginationDto findAllQuestions(Integer pageNumber);

    QuestionDto findQuestionById(Long questionId, String token);

    List<QuestionDto> findAllQuestionsByCategory(String category);

    QuestionDto createQuestion(CreateQuestionRequest request, String rawToken);

    QuestionDto updateQuestion(Long questionId, UpdateQuestionRequest request, String rawToken);

    void deleteQuestion(Long id, String rawToken);
}
