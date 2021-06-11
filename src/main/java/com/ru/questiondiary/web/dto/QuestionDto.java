package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto {
    private Long id;
    private String question;
    private LocalDate creationDate;
    private UserDto creator;

    public static QuestionDto from(Question question) {
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setCreator(UserDto.from(question.getCreator()));
        return result;
    }
}
