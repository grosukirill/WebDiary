package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.AnswerService;
import com.ru.questiondiary.web.dto.AnswerDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.request.CreateAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> createAnswer(@RequestBody CreateAnswerRequest request, @RequestHeader("Authorization") String rawToken) {
        AnswerDto answer = answerService.createAnswer(request, rawToken);
        return buildResponse(answer);
    }

    private ResponseEntity<?> buildResponse(Object answer) {
        OkResponse response = new OkResponse(answer);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
