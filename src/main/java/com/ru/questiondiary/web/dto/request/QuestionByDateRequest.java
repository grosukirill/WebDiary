package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuestionByDateRequest {
    private String date;

    @JsonCreator
    public QuestionByDateRequest(@JsonProperty String date) {
        this.date = date;
    }
}
