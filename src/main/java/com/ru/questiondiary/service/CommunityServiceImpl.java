package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.repo.CommunityRepository;
import com.ru.questiondiary.repo.CommunityUserRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;
import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.CommunityUser;
import com.ru.questiondiary.web.entity.Role;
import com.ru.questiondiary.web.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityUserRepository communityUserRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public CommunityDto createCommunity(CreateCommunityRequest request, String rawToken) {
        String token = rawToken.substring(7);
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        User user = userRepository.getById(Long.parseLong(userData.get("id")));
        List<CommunityUser> workers = new ArrayList<>();
        Optional<CommunityUser> existingCommunityUser = communityUserRepository.findByRoleAndUser(Role.ADMIN, user);
        CommunityUser communityUser;
        if (existingCommunityUser.isEmpty()) {
            communityUser = CommunityUser.builder()
                    .role(Role.ADMIN)
                    .user(user)
                    .build();
            communityUserRepository.save(communityUser);
            workers.add(communityUser);
        } else {
            workers.add(existingCommunityUser.get());
        }
        Community createdCommunity = Community.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .link("")
                .followers(new ArrayList<>())
                .questions(new ArrayList<>())
                .workers(workers)
                .build();
        communityRepository.save(createdCommunity);
        return CommunityDto.from(createdCommunity);
    }
}
