package com.ru.questiondiary.web.dto.response;

import com.ru.questiondiary.web.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private final Boolean status = true;
    private UserDto data;

    public UserResponseDto(UserDto data) {
        this.data = data;
    }
}
