package com.ru.questiondiary.web.dto.response;

import com.ru.questiondiary.web.dto.AnswerDto;
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
