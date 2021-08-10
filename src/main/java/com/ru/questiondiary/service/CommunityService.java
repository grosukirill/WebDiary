package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;

public interface CommunityService {
    CommunityDto createCommunity(CreateCommunityRequest request, String rawToken);
}
