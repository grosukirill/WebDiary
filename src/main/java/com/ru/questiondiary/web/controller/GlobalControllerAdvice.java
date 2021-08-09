package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.exception.*;
import com.ru.questiondiary.web.dto.ErrorDto;
import com.ru.questiondiary.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleAuthenticationException(AuthenticationFailedException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserDuplicateEmailException(UserDuplicateEmailException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleQuestionNotFoundException(QuestionNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
<<<<<<< HEAD
=======
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDuplicateVoteException(DuplicateVoteException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
>>>>>>> 0fc03ef5aed58a6e8a166b4a5e086e8d0684dc32
    }
}
