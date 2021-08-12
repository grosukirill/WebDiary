package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;

public interface FavoriteService {
    QuestionDto createFavorite(Long questionId, String rawToken);
}
