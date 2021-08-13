package com.ru.questiondiary.exception;

public class WrongFeedTypeException extends RuntimeException {
    public WrongFeedTypeException(String msg) {
        super(msg);
    }
}
