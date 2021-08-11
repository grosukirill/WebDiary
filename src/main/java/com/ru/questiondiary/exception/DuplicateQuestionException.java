package com.ru.questiondiary.exception;

public class DuplicateQuestionException extends RuntimeException {
    public DuplicateQuestionException(String msg) {
        super(msg);
    }
}
