package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerDto {
    private Long id;
    private String answer;
    private LocalDate date;

    public static AnswerDto from(Answer answer) {
        AnswerDto result = new AnswerDto();
        result.id = answer.getId();
        result.answer = answer.getAnswer();
        result.date = answer.getDate();
        return result;
    }
}
