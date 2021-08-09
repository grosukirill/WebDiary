package com.ru.questiondiary.exception;

public class DuplicateVoteException extends RuntimeException {
    public DuplicateVoteException(String msg) {
        super(msg);
    }
}
