package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnswerResponseDto {
    private final Boolean status = true;
    private AnswerDto data;

    public AnswerResponseDto(AnswerDto data) {
        this.data = data;
    }
}
