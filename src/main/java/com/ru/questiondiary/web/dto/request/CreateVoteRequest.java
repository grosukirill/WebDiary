package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateVoteRequest {
    private Long questionId;
    private Integer vote;
    private Long userId;

    @JsonCreator
    public CreateVoteRequest(@JsonProperty Long questionId, @JsonProperty Integer vote, @JsonProperty Long userId) {
        this.questionId = questionId;
        this.vote = vote;
        this.userId = userId;
    }
}
