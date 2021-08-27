package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.CategoryDto;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.dto.request.QuestionByDateRequest;
import com.ru.questiondiary.web.dto.request.UpdateQuestionRequest;

import java.util.List;

public interface QuestionService {
    PaginationDto findAllQuestions(Integer pageNumber, String rawToken);

    QuestionDto findQuestionById(Long questionId, String token);

    List<QuestionDto> findAllQuestionsByCategory(Long category, String rawToken, Integer pageNumber);

    QuestionDto createQuestion(CreateQuestionRequest request, String rawToken);

    QuestionDto updateQuestion(Long questionId, UpdateQuestionRequest request, String rawToken);

    void deleteQuestion(Long id, String rawToken);

    PaginationDto findFeed(String type, Integer pageNumber, String rawToken);

    List<CategoryDto> findAllCategories();

    PaginationDto findAllQuestionsByDate(Integer page, QuestionByDateRequest request, String rawToken);

    PaginationDto findAllFavoriteQuestions(Integer pageNumber, String rawToken);

    PaginationDto findNewQuestions(Integer pageNumber, String rawToken);
}
