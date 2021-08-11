package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateQuestionRequest {
    private String question;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty String question) {
        this.question = question;
    }
}
