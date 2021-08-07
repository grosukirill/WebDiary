package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuestionResponseDto {
    private final Boolean status = true;
    private QuestionDto data;

    public QuestionResponseDto(QuestionDto data) {
        this.data = data;
    }
}
