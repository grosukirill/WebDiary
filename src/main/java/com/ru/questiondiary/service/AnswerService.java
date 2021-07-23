package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.request.CreateAnswerRequest;

public interface AnswerService {
    void createAnswer(CreateAnswerRequest request);
}
