package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.EditPasswordRequest;
import com.ru.questiondiary.web.dto.request.LoginRequest;

public interface AuthenticationService {
    UserLoginDto authenticate(LoginRequest loginRequest);

    UserLoginDto editPassword(EditPasswordRequest request, String rawToken);
}
