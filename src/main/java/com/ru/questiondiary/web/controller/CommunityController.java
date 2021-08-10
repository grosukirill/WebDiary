package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.CommunityService;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<?> createCommunity(@RequestBody CreateCommunityRequest request, @RequestHeader("Authorization") String rawToken) {
        CommunityDto community = communityService.createCommunity(request, rawToken);
        OkResponse response = new OkResponse(community);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
