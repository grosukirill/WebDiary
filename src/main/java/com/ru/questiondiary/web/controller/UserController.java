package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.UserService;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.UserLoginResponseDto;
import com.ru.questiondiary.web.dto.UsersResponseDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        List<UserDto> allUsers = userService.findAllUsers();
        UsersResponseDto usersResponseDto = new UsersResponseDto(allUsers);
        return ResponseEntity.status(HttpStatus.OK).body(usersResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        UserLoginDto user = userService.register(request);
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userLoginResponseDto);
    }
}
