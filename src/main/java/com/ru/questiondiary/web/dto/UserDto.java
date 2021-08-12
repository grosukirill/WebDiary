package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto extends ResponseData {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public static UserDto from(User user) {
        UserDto result = new UserDto();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.avatar = user.getAvatar();
        return result;
    }
}
