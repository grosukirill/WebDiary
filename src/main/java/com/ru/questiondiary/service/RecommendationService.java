package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;

import java.util.List;


public interface RecommendationService {
    QuestionDto findRecommendations(User user, List<Vote> votes);
}
