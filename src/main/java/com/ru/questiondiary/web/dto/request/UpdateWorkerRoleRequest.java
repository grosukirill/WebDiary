package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.questiondiary.web.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateWorkerRoleRequest {
    private Long communityUserId;
    private Role newRole;

    @JsonCreator
    public UpdateWorkerRoleRequest(@JsonProperty Long communityUserId, @JsonProperty String newRole) {
        this.communityUserId = communityUserId;
        this.newRole = Role.valueOf(newRole);
    }
}
