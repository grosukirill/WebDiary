package com.ru.questiondiary.exception;

public class CommunityUserNotFoundException extends RuntimeException {
    public CommunityUserNotFoundException(String msg) {
        super(msg);
    }
}
