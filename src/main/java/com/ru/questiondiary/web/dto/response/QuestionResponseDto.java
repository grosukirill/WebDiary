package com.ru.questiondiary.web.dto.response;

import com.ru.questiondiary.web.dto.QuestionDto;
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
