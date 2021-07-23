package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateAnswerRequest {
    private String answer;
    private Long questionId;
    private Long userId;

    @JsonCreator
    public CreateAnswerRequest(@JsonProperty("answer") String answer,
                               @JsonProperty("questionId") Long questionId,
                               @JsonProperty("userId") Long userId) {
        this.answer = answer;
        this.questionId = questionId;
        this.userId = userId;
    }
}
