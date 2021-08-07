package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class QuestionsResponseDto {
    private final Boolean status = true;
    private List<QuestionDto> data;

    public QuestionsResponseDto(List<QuestionDto> data) {
        this.data = data;
    }
}
