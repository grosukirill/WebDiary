package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponseDto {
    private final Boolean status = false;
    private ErrorDto error;

    public ErrorResponseDto(ErrorDto error) {
        this.error = error;
    }
}
