package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<UserDto> findAllUsers();

    UserDto register(RegisterRequest request);
}
