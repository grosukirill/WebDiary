package com.ru.questiondiary.service;

import com.ru.questiondiary.web.entity.User;

import java.util.Map;

public interface TokenService {
    Map<String, String> getUserDataFromToken(String token);

    String createToken(User user);
}
