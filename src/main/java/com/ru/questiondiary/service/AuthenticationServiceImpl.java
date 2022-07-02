package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.AuthenticationFailedException;
import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.EditPasswordRequest;
import com.ru.questiondiary.web.dto.request.LoginRequest;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserLoginDto authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return UserLoginDto.from(user, tokenService.createToken(user));
        } else {
            throw new AuthenticationFailedException("Couldn't authenticate this user");
        }
    }

    @Override
    public UserLoginDto editPassword(EditPasswordRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),
                        request.getOldPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationFailedException("Couldn't authenticate this user");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        String token = tokenService.createToken(user);
        return UserLoginDto.from(user, token);
    }

    private User getUserFromToken(String token) {
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Token [%s] has no linked user", token));
        }
        return user.get();
    }
}
