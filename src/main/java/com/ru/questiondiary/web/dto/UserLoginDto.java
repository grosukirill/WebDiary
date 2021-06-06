package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String bearer;

    public static UserLoginDto from(User user, String bearer) {
        UserLoginDto result = new UserLoginDto();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.role = user.getRole();
        result.bearer = bearer;
        return result;
    }
}
