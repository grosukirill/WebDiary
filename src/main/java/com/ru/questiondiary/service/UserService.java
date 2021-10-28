package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.DuplicateUserEmailException;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface UserService extends UserDetailsService {
    List<UserDto> findAllUsers();
    
    UserLoginDto findUserByEmail(String email);

    UserLoginDto register(RegisterRequest request) throws DuplicateUserEmailException;

    UserDto findUserById(Long id, String rawToken);

    UserDto findUserByToken(String token);

    void followUser(Long userId, String rawToken);

    List<CommunityDto> findLastFourSubscriptions(String rawToken);

    UserDto approveUser(Long userId, String rawToken);

    UserDto uploadAvatar(MultipartFile image, String rawToken) throws IOException;

    UserLoginDto createFromGoogle(Map<String, Object> attributes);
}
