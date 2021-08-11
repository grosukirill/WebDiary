package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.CommunityUserDto;
import com.ru.questiondiary.web.dto.request.AddWorkerToCommunityRequest;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;
import com.ru.questiondiary.web.dto.request.UpdateCommunityRequest;
import com.ru.questiondiary.web.dto.request.UpdateWorkerRoleRequest;

public interface CommunityService {
    CommunityDto createCommunity(CreateCommunityRequest request, String rawToken);

    CommunityDto addWorkerToCommunity(AddWorkerToCommunityRequest request);

    CommunityDto followCommunity(Long communityId, String rawToken);

    CommunityDto updateCommunity(Long communityId, UpdateCommunityRequest request, String rawToken);

    CommunityUserDto updateWorkerRole(UpdateWorkerRoleRequest request);

    CommunityDto findById(Long id);
}
