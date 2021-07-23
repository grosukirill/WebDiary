package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionByIdRequest {
    private Long userId;

    @JsonCreator
    public QuestionByIdRequest(@JsonProperty("userId") Long userId) {
        this.userId = userId;
    }
}
