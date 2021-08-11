package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateCommunityRequest {
    private String newTitle;
    private String newDescription;
    private String newLink;

    @JsonCreator
    public UpdateCommunityRequest(@JsonProperty String newTitle,
                                  @JsonProperty String newDescription,
                                  @JsonProperty String newLink) {
        this.newTitle = newTitle;
        this.newDescription = newDescription;
        this.newLink = newLink;
    }
}
