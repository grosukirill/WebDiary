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
        ErrorDto errorDto = new ErrorDto(e.getMessage(),
                ErrorCode.AUTHENTICATION_ERROR.number,
                ErrorCode.AUTHENTICATION_ERROR,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(),
                ErrorCode.BAD_CREDENTIALS.number,
                ErrorCode.BAD_CREDENTIALS,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserDuplicateEmailException(DuplicateUserEmailException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(),
                ErrorCode.DUPLICATE_EMAIL.number,
                ErrorCode.DUPLICATE_EMAIL,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleQuestionNotFoundException(QuestionNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(),
                ErrorCode.QUESTION_NOT_FOUND.number,
                ErrorCode.QUESTION_NOT_FOUND,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(),
                ErrorCode.USER_NOT_FOUND.number,
                ErrorCode.USER_NOT_FOUND,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDuplicateVoteException(DuplicateVoteException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.DUPLICATE_VOTE.number, ErrorCode.DUPLICATE_VOTE, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleTokenValidationException(TokenValidationException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.INVALID_TOKEN.number, ErrorCode.INVALID_TOKEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCommunityNotFoundException(CommunityNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.COMMUNITY_NOT_FOUND.number, ErrorCode.COMMUNITY_NOT_FOUND, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleQuestionDuplicateException(DuplicateQuestionException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.DUPLICATE_QUESTION.number, ErrorCode.DUPLICATE_QUESTION, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleForeignQuestionUpdateException(ForeignQuestionUpdateException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.FOREIGN_QUESTION_UPDATE.number, ErrorCode.FOREIGN_QUESTION_UPDATE, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleForeignQuestionDeleteException(ForeignQuestionDeleteException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.FOREIGN_QUESTION_DELETE.number, ErrorCode.FOREIGN_QUESTION_DELETE, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCommunityUserNotFoundException(CommunityUserNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.COMMUNITY_USER_NOT_FOUND.number, ErrorCode.COMMUNITY_USER_NOT_FOUND, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleAuthoritiesGrantedException(AuthoritiesGrantedException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), ErrorCode.AUTHORITIES_GRANTED.number, ErrorCode.AUTHORITIES_GRANTED, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        ErrorResponse response = new ErrorResponse(errorDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
