package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.UserDuplicateEmailException;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user: users) {
            userDtos.add(UserDto.from(user));
        }
        return userDtos;
    }

    @Override
    public UserDto register(RegisterRequest request) throws UserDuplicateEmailException {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserDuplicateEmailException(String.format("User with email %s already exists. Consider logging in.", request.getEmail()));
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(hashedPassword)
                .role("USER")
                .build();
        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }
}
