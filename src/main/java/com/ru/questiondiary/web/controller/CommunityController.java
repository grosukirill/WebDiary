package com.ru.questiondiary.web.controller;

import com.ru.questiondiary.service.CommunityService;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.CommunityUserDto;
import com.ru.questiondiary.web.dto.OkResponse;
import com.ru.questiondiary.web.dto.request.AddWorkerToCommunityRequest;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;
import com.ru.questiondiary.web.dto.request.UpdateCommunityRequest;
import com.ru.questiondiary.web.dto.request.UpdateWorkerRoleRequest;
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
        return buildResponse(community);
    }

    @PostMapping("/workers")
    public ResponseEntity<?> addWorkerToCommunity(@RequestBody AddWorkerToCommunityRequest request) {
        CommunityDto community = communityService.addWorkerToCommunity(request);
        return buildResponse(community);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> followCommunity(@PathVariable("id") Long communityId, @RequestHeader("Authorization") String rawToken) {
        CommunityDto community = communityService.followCommunity(communityId, rawToken);
        return buildResponse(community);
    }

    @PutMapping
    public ResponseEntity<?> updateCommunity(@RequestParam("communityId") Long communityId, @RequestBody UpdateCommunityRequest request, @RequestHeader("Authorization") String rawToken) {
        CommunityDto community = communityService.updateCommunity(communityId, request, rawToken);
        return buildResponse(community);
    }

    @PutMapping("/workers")
    public ResponseEntity<?> updateWorkerRole(@RequestBody UpdateWorkerRoleRequest request) {
        CommunityUserDto communityUser = communityService.updateWorkerRole(request);
        return buildResponse(communityUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCommunity(@PathVariable("id") Long id) {
        CommunityDto community = communityService.findById(id);
        return buildResponse(community);
    }

    private ResponseEntity<?> buildResponse(Object community) {
        OkResponse response = new OkResponse(community);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
