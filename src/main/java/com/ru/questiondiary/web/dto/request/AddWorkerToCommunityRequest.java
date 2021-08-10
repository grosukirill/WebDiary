package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.questiondiary.web.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddWorkerToCommunityRequest {
    private Long userId;
    private Long communityId;
    private Role role;

    @JsonCreator
    public AddWorkerToCommunityRequest(@JsonProperty Long userId, @JsonProperty Long communityId, @JsonProperty String role) {
        this.userId = userId;
        this.communityId = communityId;
        this.role = Role.valueOf(role);
    }
}
