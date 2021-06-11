package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.QuestionService;
import com.ru.questiondiary.web.dto.QuestionDto;
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
    public ResponseEntity<?> getAllQuestions() {
        List<QuestionDto> questionDtos = questionService.getAllQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(questionDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable("id") Long questionId) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(questionDto);
    }
}
