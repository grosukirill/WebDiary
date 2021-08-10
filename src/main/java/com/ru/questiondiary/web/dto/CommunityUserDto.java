package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.CommunityUser;
import com.ru.questiondiary.web.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunityUserDto {
    private Long id;
    private Role role;
    private UserDto userDto;

    public static CommunityUserDto from(CommunityUser communityUser) {
        CommunityUserDto result = new CommunityUserDto();
        result.setId(communityUser.getId());
        result.setRole(communityUser.getRole());
        result.setUserDto(UserDto.from(communityUser.getUser()));
        return result;
    }
}
