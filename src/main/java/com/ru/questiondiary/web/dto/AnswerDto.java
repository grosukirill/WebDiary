package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerDto extends ResponseData {
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
