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
    public CreateAnswerRequest(@JsonProperty String answer,
                               @JsonProperty Long questionId,
                               @JsonProperty Long userId) {
        this.answer = answer;
        this.questionId = questionId;
        this.userId = userId;
    }
}
