package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.exception.DuplicateUserEmailException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
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
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

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
    @Transactional
    public UserLoginDto register(RegisterRequest request) throws DuplicateUserEmailException {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateUserEmailException(String.format("User with email %s already exists. Consider logging in.", request.getEmail()));
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
        String token = tokenService.createToken(user);
        return UserLoginDto.from(user, token);
    }

    @Override
    @Transactional
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", id));
        }
        return UserDto.from(user.get());
    }

    @Override
    @Transactional
    public UserDto findUserByToken(String token) {
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Token [%s] has no linked user", token));
        }
        return UserDto.from(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }
}
