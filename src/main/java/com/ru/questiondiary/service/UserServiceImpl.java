package com.ru.questiondiary.service;

import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
