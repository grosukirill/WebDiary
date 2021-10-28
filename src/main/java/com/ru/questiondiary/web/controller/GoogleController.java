package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.UserService;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> register(OAuth2AuthenticationToken principal) {
        String email = principal.getPrincipal().getAttribute("email");
        try {
            userService.loadUserByUsername(email);
            UserLoginDto userLoginDto = userService.findUserByEmail(email);
            return buildResponse(userLoginDto);
        } catch (UsernameNotFoundException e) {
            UserLoginDto userLoginDto = userService.createFromGoogle(principal.getPrincipal().getAttributes());
            return buildResponse(userLoginDto);
        }
    }

    private ResponseEntity<?> buildResponse(Object data) {
        OkResponse response = new OkResponse(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
