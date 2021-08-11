package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.exception.*;
import com.ru.questiondiary.web.dto.ErrorCode;
import com.ru.questiondiary.web.dto.ErrorDto;
import com.ru.questiondiary.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleAuthenticationException(AuthenticationFailedException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.AUTHENTICATION_ERROR, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.BAD_CREDENTIALS, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserDuplicateEmailException(DuplicateUserEmailException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.DUPLICATE_EMAIL, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleQuestionNotFoundException(QuestionNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.QUESTION_NOT_FOUND, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.USER_NOT_FOUND, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDuplicateVoteException(DuplicateVoteException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.DUPLICATE_VOTE, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleTokenValidationException(TokenValidationException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.INVALID_TOKEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCommunityNotFoundException(CommunityNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.COMMUNITY_NOT_FOUND, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleQuestionDuplicateException(DuplicateQuestionException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.DUPLICATE_QUESTION, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
