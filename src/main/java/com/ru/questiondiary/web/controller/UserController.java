package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.UserService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
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
        OkResponse response = new OkResponse(allUsers);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        UserLoginDto user = userService.register(request);
        OkResponse response = new OkResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long id) {
        UserDto user = userService.findUserById(id);
        OkResponse response = new OkResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<?> findUserByToken(@PathVariable("token") String token) {
        UserDto user = userService.findUserByToken(token);
        OkResponse response = new OkResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
