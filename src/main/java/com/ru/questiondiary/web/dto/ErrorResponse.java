package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponse {
    private final Boolean status = false;
    private ErrorDto error;

    public ErrorResponse(ErrorDto error) {
        this.error = error;
    }
}
