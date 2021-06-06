package com.ru.questiondiary.service;

import com.ru.questiondiary.web.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<User> findAllUsers();
}
