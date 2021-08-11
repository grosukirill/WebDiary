package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.QuestionService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> findAllQuestions(@RequestParam("page") Integer pageNumber) {
        PaginationDto questionDtos = questionService.findAllQuestions(pageNumber);
        OkResponse response = new OkResponse(questionDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findQuestionById(@PathVariable("id") Long questionId, @RequestParam("token") String token) {
        QuestionDto questionDto = questionService.findQuestionById(questionId, token);
        OkResponse response = new OkResponse(questionDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/category")
    public ResponseEntity<?> findAllQuestionByCategory(@RequestParam("category") String category) {
        List<QuestionDto> questions = questionService.findAllQuestionsByCategory(category);
        OkResponse response = new OkResponse(questions);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody CreateQuestionRequest request) {
        QuestionDto questionDto = questionService.createQuestion(request);
        OkResponse response = new OkResponse(questionDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
