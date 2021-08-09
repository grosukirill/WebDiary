package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateQuestionRequest {
    private String question;
    private String token;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty String question,
                                 @JsonProperty String token) {
        this.question = question;
        this.token = token;
    }
}
