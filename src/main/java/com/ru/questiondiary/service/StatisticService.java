package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.PaginationDto;

public interface StatisticService {
    PaginationDto getStatisticsByViews(String rawToken, Integer pageNumber);

    PaginationDto getStatisticsByAnswers(String rawToken, Integer pageNumber);

    PaginationDto getStatisticsByCategories(String rawToken, Integer pageNumber);

    PaginationDto getStatisticsByVotes(String rawToken, Integer pageNumber);
}
