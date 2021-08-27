package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateVoteRequest {
    private Integer vote;
    private Long questionId;

    @JsonCreator
    public CreateVoteRequest(@JsonProperty Integer vote, @JsonProperty Long questionId) {
        this.vote = vote;
        this.questionId = questionId;
    }

}
