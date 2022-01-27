package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.CommunityRepository;
import com.ru.questiondiary.repo.CommunityUserRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.CommunityUser;
import com.ru.questiondiary.web.entity.Role;
import com.ru.questiondiary.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class CommunityUserRepositoryTest {
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Test
    void test_create() {
        //ARRANGE
        CommunityUser communityUser = CommunityUser.builder().build();
        //ACT
        CommunityUser saved = communityUserRepository.save(communityUser);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        CommunityUser communityUser = communityUserRepository.save(CommunityUser.builder().build());
        //ACT
        boolean existsById = communityUserRepository.existsById(communityUser.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        CommunityUser communityUser = communityUserRepository.save(CommunityUser.builder().build());
        //ACT
        communityUser.setRole(Role.ADMIN);
        CommunityUser updated = communityUserRepository.save(communityUser);
        //ASSERT
        assertThat(updated.getRole().equals(Role.ADMIN)).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        CommunityUser communityUser = communityUserRepository.save(CommunityUser.builder().build());
        //ACT
        communityUserRepository.delete(communityUser);
        //ASSERT
        assertThat(communityUserRepository.existsById(communityUser.getId())).isFalse();
    }

    @Test
    void test_findByRoleAndUser() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        CommunityUser communityUser = communityUserRepository.save(CommunityUser.builder().role(Role.ADMIN).user(user).build());
        //ACT
        Optional<CommunityUser> found = communityUserRepository.findByRoleAndUser(Role.ADMIN, user);
        //ASSERT
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getId().equals(communityUser.getId())).isTrue();
    }

    @Test
    void test_findByUserAndCommunities() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Community community = communityRepository.save(Community.builder().build());
        communityUserRepository.save(CommunityUser.builder().user(user).communities(Collections.singletonList(community)).build());
        //ACT
        Optional<CommunityUser> found = communityUserRepository.findByUserAndCommunities(user, community);
        //ASSERT
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUser().equals(user)).isTrue();
        assertThat(found.get().getCommunities()).contains(community);
    }

    @Test
    void test_findByUserAndRoleAndCommunities() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Community community = communityRepository.save(Community.builder().build());
        communityUserRepository.save(CommunityUser.builder().user(user).communities(Collections.singletonList(community)).role(Role.ADMIN).build());
        //ACT
        Optional<CommunityUser> found = communityUserRepository.findByUserAndRoleAndCommunities(user, Role.ADMIN, community);
        //ASSERT
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUser().equals(user)).isTrue();
        assertThat(found.get().getCommunities()).contains(community);
        assertThat(found.get().getRole().equals(Role.ADMIN));
    }

    @Test
    void test_deleteAllByCommunity() {
        //ARRANGE
        Community community = communityRepository.save(Community.builder().build());
        CommunityUser communityUser = communityUserRepository.save(CommunityUser.builder().communities(Collections.singletonList(community)).build());
        CommunityUser communityUser2  = communityUserRepository.save(CommunityUser.builder().build());
        //ACT
        communityUserRepository.deleteAllByCommunities(community);
        //ASSERT
        assertThat(communityUserRepository.existsById(communityUser.getId())).isFalse();
        assertThat(communityUserRepository.existsById(communityUser2.getId())).isTrue();
    }
}
