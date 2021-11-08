package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.StatisticService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticService statisticService;


    @GetMapping("/views")
    public ResponseEntity<?> getQuestionsViewsStatistics(@RequestHeader("Authorization") String rawToken, @RequestParam("page") Integer pageNumber) {
        PaginationDto data = statisticService.getStatisticsByViews(rawToken, pageNumber);
        return buildResponse(data);
    }

    @GetMapping("/answers")
    public ResponseEntity<?> getAnswersAnswers(@RequestHeader("Authorization") String rawToken, @RequestParam("page") Integer pageNumber) {
        PaginationDto data = statisticService.getStatisticsByAnswers(rawToken, pageNumber);
        return buildResponse(data);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategoriesStatistics(@RequestHeader("Authorization") String rawToken, @RequestParam("page") Integer pageNumber) {
        PaginationDto data = statisticService.getStatisticsByCategories(rawToken, pageNumber);
        return buildResponse(data);
    }

    @GetMapping("/votes")
    public ResponseEntity<?> getVotesStatistics(@RequestHeader("Authorization") String rawToken, @RequestParam("page") Integer pageNumber) {
        PaginationDto data = statisticService.getStatisticsByVotes(rawToken, pageNumber);
        return buildResponse(data);
    }

    private ResponseEntity<?> buildResponse(Object data) {
        OkResponse response = new OkResponse(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
