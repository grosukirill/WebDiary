package com.ru.questiondiary.exception;

public class CommunityNotFoundException extends RuntimeException {
    public CommunityNotFoundException(String msg) {
        super(msg);
    }
}
