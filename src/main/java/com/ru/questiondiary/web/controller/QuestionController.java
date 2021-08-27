package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.QuestionService;
import com.ru.questiondiary.web.dto.CategoryDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.dto.request.QuestionByDateRequest;
import com.ru.questiondiary.web.dto.request.UpdateQuestionRequest;
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
    public ResponseEntity<?> findAllQuestions(@RequestParam("page") Integer pageNumber, @RequestHeader("Authorization") String rawToken) {
        PaginationDto questionDtos = questionService.findAllQuestions(pageNumber, rawToken);
        return buildResponse(questionDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findQuestionById(@PathVariable("id") Long questionId, @RequestHeader("Authorization") String rawToken) {
        QuestionDto questionDto = questionService.findQuestionById(questionId, rawToken);
        return buildResponse(questionDto);
    }

    @GetMapping("/category")
    public ResponseEntity<?> findAllQuestionByCategory(@RequestParam("categoryId") Long categoryId, @RequestHeader("Authorization") String rawToken) {
        List<QuestionDto> questions = questionService.findAllQuestionsByCategory(categoryId, rawToken);
        return buildResponse(questions);
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody CreateQuestionRequest request, @RequestHeader("Authorization") String rawToken) {
        QuestionDto questionDto = questionService.createQuestion(request, rawToken);
        return buildResponse(questionDto);
    }

    @PutMapping
    public ResponseEntity<?> updateQuestion(@RequestParam("questionId") Long questionId, @RequestBody UpdateQuestionRequest request, @RequestHeader("Authorization") String rawToken) {
        QuestionDto question = questionService.updateQuestion(questionId, request, rawToken);
        return buildResponse(question);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteQuestion(@RequestParam("questionId") Long id, @RequestHeader("Authorization") String rawToken) {
        questionService.deleteQuestion(id, rawToken);
        OkResponse response = new OkResponse();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> findFeed(@RequestParam("type") String type,
                                      @RequestParam("page") Integer pageNumber,
                                      @RequestHeader("Authorization") String rawToken) {
        PaginationDto questions = questionService.findFeed(type, pageNumber, rawToken);
        return buildResponse(questions);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> findAllCategories() {
        List<CategoryDto> categories = questionService.findAllCategories();
        return buildResponse(categories);
    }

    @GetMapping("/date")
    public ResponseEntity<?> findAllQuestionByDate(@RequestParam("page") Integer page, @RequestBody QuestionByDateRequest request, @RequestHeader("Authorization") String rawToken) {
        PaginationDto questions = questionService.findAllQuestionsByDate(page, request, rawToken);
        return buildResponse(questions);
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> findAllFavoriteQuestions(@RequestParam("page") Integer pageNumber,@RequestHeader("Authorization") String rawToken) {
        PaginationDto questions = questionService.findAllFavoriteQuestions(pageNumber, rawToken);
        return buildResponse(questions);
    }


    private ResponseEntity<?> buildResponse(Object question) {
        OkResponse response = new OkResponse(question);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
