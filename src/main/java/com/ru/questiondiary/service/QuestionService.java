package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAllQuestions();
}
