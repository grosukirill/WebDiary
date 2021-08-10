package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.UserDuplicateEmailException;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<UserDto> findAllUsers();

    UserLoginDto register(RegisterRequest request) throws UserDuplicateEmailException;

    UserDto findUserById(Long id);

    UserDto findUserByToken(String token);
}
