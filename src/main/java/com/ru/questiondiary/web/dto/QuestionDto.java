package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto extends ResponseData {
    private Long id;
    private String question;
    private LocalDate creationDate;
    private UserDto creator;
    private List<AnswerDto> answers;
    private Integer votes;

    public static QuestionDto from(Question question, List<Answer> answers) {
        List<AnswerDto> answerDtos = new ArrayList<>();
        for (Answer answer: answers) {
            answerDtos.add(AnswerDto.from(answer));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setCreator(UserDto.from(question.getCreator()));
        result.setAnswers(answerDtos);
        return result;
    }

    public static QuestionDto from(Question question) {
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setCreator(UserDto.from(question.getCreator()));
        result.setVotes(question.getCountOfVotes());
        return result;
    }
}
