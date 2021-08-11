package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateQuestionRequest {
    private Long questionId;
    private String newQuestion;

    @JsonCreator
    public UpdateQuestionRequest(@JsonProperty Long questionId, @JsonProperty String newQuestion) {
        this.questionId = questionId;
        this.newQuestion = newQuestion;
    }
}
