package com.ru.questiondiary.web.dto;

public enum ErrorCode {
    AUTHENTICATION_ERROR(1),
    COMMUNITY_NOT_FOUND(2),
    DUPLICATE_VOTE(3),
    DUPLICATE_QUESTION(4),
    QUESTION_NOT_FOUND(5),
    INVALID_TOKEN(6),
    DUPLICATE_EMAIL(7),
    USER_NOT_FOUND(8),
    BAD_CREDENTIALS(9),
    FOREIGN_QUESTION_UPDATE(10),
    FOREIGN_QUESTION_DELETE(11),
    COMMUNITY_USER_NOT_FOUND(12),
    AUTHORITIES_GRANTED(13),
    WRONG_FEED_TYPE(14),
    CATEGORY_NOT_FOUND(15);

    public final Integer number;

    ErrorCode(Integer number) {
        this.number = number;
    }
}
