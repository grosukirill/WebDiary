package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.VoteService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<?> createVote(@RequestBody CreateVoteRequest request, @RequestHeader("Authorization") String rawToken) {
        QuestionDto question = voteService.createVote(request, rawToken);
        return getResponseEntity(question);
    }

    private ResponseEntity<?> getResponseEntity(Object question) {
        OkResponse response = new OkResponse(question);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
