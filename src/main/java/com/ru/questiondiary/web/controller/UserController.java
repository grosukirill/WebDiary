package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.UserService;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return buildResponse(allUsers);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        UserLoginDto user = userService.register(request);
        return buildResponse(user);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String rawToken) {
        UserDto user = userService.findUserById(id, rawToken);
        return buildResponse(user);
    }

    @GetMapping("/token")
    public ResponseEntity<?> findUserByToken(@RequestHeader("Authorization") String token) {
        UserDto user = userService.findUserByToken(token);
        return buildResponse(user);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> followUser(@PathVariable("id") Long userId, @RequestHeader("Authorization") String rawToken) {
        userService.followUser(userId, rawToken);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse());
    }

    @GetMapping("/lastFourSubscriptions")
    public ResponseEntity<?> getLastFourSubscriptions(@RequestHeader("Authorization") String rawToken) {
        List<CommunityDto> communityDtos = userService.findLastFourSubscriptions(rawToken);
        return buildResponse(communityDtos);
    }

    @PutMapping("/approve")
    public ResponseEntity<?> approveUser(@RequestParam("userId") Long userId, @RequestHeader("Authorization") String rawToken) {
        UserDto user = userService.approveUser(userId, rawToken);
        return buildResponse(user);
    }

    @PutMapping(value = "/avatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadAvatar(@RequestPart("image") MultipartFile image, @RequestHeader("Authorization") String rawToken) throws IOException {
        UserDto user = userService.uploadAvatar(image, rawToken);
        return buildResponse(user);
    }

    private ResponseEntity<?> buildResponse(Object data) {
        OkResponse response = new OkResponse(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
