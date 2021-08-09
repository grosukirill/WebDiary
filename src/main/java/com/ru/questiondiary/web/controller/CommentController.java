package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.CommentService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest request) {
        QuestionDto question = commentService.createComment(request);
        OkResponse response = new OkResponse(question);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
