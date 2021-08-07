package com.ru.questiondiary.web.dto.response;

import com.ru.questiondiary.web.dto.UserLoginDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginResponseDto {
    private final Boolean status = true;
    private UserLoginDto data;

    public UserLoginResponseDto(UserLoginDto data) {
        this.data = data;
    }
}
