package com.ru.questiondiary.exception;

public class QuestionDuplicateException extends RuntimeException {
    public QuestionDuplicateException(String msg) {
        super(msg);
    }
}
