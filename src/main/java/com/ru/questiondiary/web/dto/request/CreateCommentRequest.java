package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateCommentRequest {
    private String comment;
    private Long questionId;
    private Long userId;

    @JsonCreator
    public CreateCommentRequest(@JsonProperty("comment") String comment, @JsonProperty("questionId") Long questionId, @JsonProperty("userId") Long userId) {
        this.comment = comment;
        this.questionId = questionId;
        this.userId = userId;
    }
}
