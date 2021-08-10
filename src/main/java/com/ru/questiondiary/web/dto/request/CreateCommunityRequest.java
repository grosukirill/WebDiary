package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateCommunityRequest {
    private String title;
    private String description;

    @JsonCreator
    public CreateCommunityRequest(@JsonProperty String title, @JsonProperty String description) {
        this.title = title;
        this.description = description;
    }
}
