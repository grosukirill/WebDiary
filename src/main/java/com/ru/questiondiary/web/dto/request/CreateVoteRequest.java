package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateVoteRequest {
    private Integer vote;
    private Long userId;
    private Long questionId;

    @JsonCreator
    public CreateVoteRequest(@JsonProperty Integer vote, @JsonProperty Long userId, @JsonProperty Long questionId) {
        this.vote = vote;
        this.userId = userId;
        this.questionId = questionId;
    }
}
