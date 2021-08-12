package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.FavoriteService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> createFavorite(@RequestParam("questionId") Long questionId, @RequestHeader("Authorization") String rawToken) {
        QuestionDto questionDto = favoriteService.createFavorite(questionId, rawToken);
        return buildResponse(questionDto);
    }

    private ResponseEntity<?> buildResponse(Object data) {
        OkResponse response = new OkResponse(data);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
