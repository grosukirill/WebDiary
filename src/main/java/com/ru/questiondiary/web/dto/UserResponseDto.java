package com.ru.questiondiary.web.dto;

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
