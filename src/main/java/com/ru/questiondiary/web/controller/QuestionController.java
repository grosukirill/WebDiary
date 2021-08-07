package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.QuestionService;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.response.QuestionResponseDto;
import com.ru.questiondiary.web.dto.response.QuestionsResponseDto;
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
    public ResponseEntity<?> findAllQuestions() {
        List<QuestionDto> questionDtos = questionService.findAllQuestions();
        QuestionsResponseDto questionsResponseDto = new QuestionsResponseDto(questionDtos);
        return ResponseEntity.status(HttpStatus.OK).body(questionsResponseDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findQuestionById(@PathVariable("id") Long questionId, @RequestParam("userId") Long userId) {
        QuestionDto questionDto = questionService.findQuestionById(questionId, userId);
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(questionDto);
        return ResponseEntity.status(HttpStatus.OK).body(questionResponseDto);
    }

    @GetMapping("/category")
    public ResponseEntity<?> findAllQuestionByCategory(@RequestParam("category") String category) {
        List<QuestionDto> questions = questionService.findAllQuestionsByCategory(category);
        QuestionsResponseDto questionsResponseDto = new QuestionsResponseDto(questions);
        return ResponseEntity.status(HttpStatus.OK).body(questionsResponseDto);
    }
}
