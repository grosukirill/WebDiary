package com.ru.questiondiary.service;

import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;


public interface RecommendationService {
    List<RecommendedItem> findRecommendations(User user, List<Vote> votes);
}
