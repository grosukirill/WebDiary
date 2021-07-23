package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.AnswerDto;
import com.ru.questiondiary.web.dto.request.CreateAnswerRequest;

public interface AnswerService {
    AnswerDto createAnswer(CreateAnswerRequest request);
}
