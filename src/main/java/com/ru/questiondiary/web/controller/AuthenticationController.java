package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.AuthenticationService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserLoginDto authenticatedUser = authenticationService.authenticate(loginRequest);
        OkResponse response = new OkResponse(authenticatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
