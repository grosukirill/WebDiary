package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.exception.AuthenticationFailedException;
import com.ru.questiondiary.exception.UserDuplicateEmailException;
import com.ru.questiondiary.web.dto.ErrorDto;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserDuplicateEmailException(UserDuplicateEmailException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
