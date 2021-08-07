package com.ru.questiondiary.web.dto.response;

import com.ru.questiondiary.web.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UsersResponseDto {
    private final Boolean status = true;
    private List<UserDto> data;

    public UsersResponseDto(List<UserDto> data) {
        this.data = data;
    }
}
