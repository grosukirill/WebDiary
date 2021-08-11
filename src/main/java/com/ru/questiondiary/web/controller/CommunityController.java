package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.CommunityService;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.request.AddWorkerToCommunityRequest;
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

    @PostMapping("/workers")
    public ResponseEntity<?> addWorkerToCommunity(@RequestBody AddWorkerToCommunityRequest request) {
        CommunityDto community = communityService.addWorkerToCommunity(request);
        OkResponse response = new OkResponse(community);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> followCommunity(@PathVariable("id") Long communityId, @RequestHeader("Authorization") String rawToken) {
        CommunityDto community = communityService.followCommunity(communityId, rawToken);
        OkResponse response = new OkResponse(community);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
