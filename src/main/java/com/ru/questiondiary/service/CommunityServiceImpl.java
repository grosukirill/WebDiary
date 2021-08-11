package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.AuthoritiesGrantedException;
import com.ru.questiondiary.exception.CommunityNotFoundException;
import com.ru.questiondiary.exception.CommunityUserNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.CommunityRepository;
import com.ru.questiondiary.repo.CommunityUserRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.request.AddWorkerToCommunityRequest;
import com.ru.questiondiary.web.dto.request.CreateCommunityRequest;
import com.ru.questiondiary.web.dto.request.UpdateCommunityRequest;
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
        User user = getUserFromToken(rawToken);
        List<CommunityUser> workers = new ArrayList<>();
        Optional<CommunityUser> existingCommunityUser = communityUserRepository.findByRoleAndUser(Role.ADMIN, user);
        CommunityUser communityUser = new CommunityUser();
        if (existingCommunityUser.isEmpty()) {
            communityUser = CommunityUser.builder()
                    .role(Role.ADMIN)
                    .user(user)
                    .communities(new ArrayList<>())
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
        List<Community> communities = communityUser.getCommunities();
        communities.add(createdCommunity);
        communityUser.setCommunities(communities);
        communityUserRepository.save(communityUser);
        return CommunityDto.from(createdCommunity);
    }

    @Override
    @Transactional
    public CommunityDto addWorkerToCommunity(AddWorkerToCommunityRequest request) {
        Optional<Community> community = communityRepository.findById(request.getCommunityId());
        if (community.isEmpty()) {
            throw new CommunityNotFoundException(String.format("Community with ID [%s] not found", request.getCommunityId()));
        }
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", request.getUserId()));
        }
        Optional<CommunityUser> existingCommunityUser = communityUserRepository.findByRoleAndUser(request.getRole(), user.get());
        List<CommunityUser> workers = community.get().getWorkers();
        CommunityUser communityUser;
        if (existingCommunityUser.isEmpty()) {
            communityUser = CommunityUser.builder()
                    .role(request.getRole())
                    .user(user.get())
                    .communities(new ArrayList<>())
                    .build();
            communityUserRepository.save(communityUser);
        } else {
            communityUser = existingCommunityUser.get();
        }
        workers.add(communityUser);
        community.get().setWorkers(workers);
        communityRepository.save(community.get());
        List<Community> communities = communityUser.getCommunities();
        communities.add(community.get());
        communityUser.setCommunities(communities);
        communityUserRepository.save(communityUser);
        return CommunityDto.from(community.get());
    }

    @Override
    @Transactional
    public CommunityDto followCommunity(Long communityId, String rawToken) {
        Optional<Community> community = communityRepository.findById(communityId);
        if (community.isEmpty()) {
            throw new CommunityNotFoundException(String.format("Community with ID [%s] not found", communityId));
        }
        User user = getUserFromToken(rawToken);
        community.get().follow(user);
        communityRepository.save(community.get());
        return CommunityDto.from(community.get());
    }

    @Override
    @Transactional
    public CommunityDto updateCommunity(Long communityId, UpdateCommunityRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Optional<Community> community = communityRepository.findById(communityId);
        if (community.isEmpty()) {
            throw new CommunityNotFoundException(String.format("Community with ID [%s] not found", communityId));
        }
        Optional<CommunityUser> communityUser = communityUserRepository.findByUserAndCommunities(user, community.get());
        if (communityUser.isEmpty()) {
            throw new CommunityUserNotFoundException("You have no rights to update communities. You are not part of it.");
        } else if (communityUser.get().getRole() != Role.ADMIN) {
            throw new AuthoritiesGrantedException("You must be an admin to update this community");
        }
        community.get().setTitle(request.getNewTitle());
        community.get().setDescription(request.getNewDescription());
        community.get().setLink(request.getNewLink());
        communityRepository.save(community.get());
        return CommunityDto.from(community.get());
    }

    private User getUserFromToken(String rawToken) {
        Map<String, String> userData = tokenService.getUserDataFromToken(rawToken);
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userData.get("id")));
        }
        return user.get();
    }
}
