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
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class CommunityRepositoryTest {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;

    @Test
    void test_create() {
        //ARRANGE
        Community someCommunity = Community.builder().build();
        //ACT
        Community saved = communityRepository.save(someCommunity);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        Community someCommunity = communityRepository.save(Community.builder().build());
        //ACT
        boolean existsById = communityRepository.existsById(someCommunity.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        Community someCommunity = communityRepository.save(Community.builder().build());
        //ACT
        someCommunity.setTitle("Test");
        Community updated = communityRepository.save(someCommunity);
        //ASSERT
        assertThat(updated.getTitle().equals("Test")).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        Community someCommunity = communityRepository.save(Community.builder().build());
        //ACT
        communityRepository.delete(someCommunity);
        //ASSERT
        assertThat(communityRepository.existsById(someCommunity.getId())).isFalse();
    }

    @Test
    void test_findLastFourSubscription() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Community community1 = communityRepository.save(Community.builder().followers(Collections.singletonList(user)).build());
        Community community2 = communityRepository.save(Community.builder().followers(Collections.singletonList(user)).build());
        Community community3 = communityRepository.save(Community.builder().followers(Collections.singletonList(user)).build());
        Community community4 = communityRepository.save(Community.builder().followers(Collections.singletonList(user)).build());
        Community community5 = communityRepository.save(Community.builder().followers(Collections.singletonList(user)).build());
        //ACT
        List<Community> communityList = communityRepository.findLastFourSubscriptions(user.getId());
        //ASSERT
        assertThat(communityList.size()==4).isTrue();
        assertThat(communityList).contains(community2, community3, community4, community5);
        assertThat(communityList).doesNotContain(community1);
    }

    @Test
    void test_findCommunitiesWhereUserIdAdmin() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        User user1 = userRepository.save(User.builder().build());
        Community communityOK = communityRepository.save(Community.builder().build());
        Community communityNOTADMIN = communityRepository.save(Community.builder().build());
        Community communityNOTUSER = communityRepository.save(Community.builder().build());
        CommunityUser communityUserOK = communityUserRepository.save(CommunityUser.builder().communities(Collections.singletonList(communityOK)).user(user).role(Role.ADMIN).build());
        CommunityUser communityUserWRONGROLE = communityUserRepository.save(CommunityUser.builder().communities(Collections.singletonList(communityNOTADMIN)).user(user).role(Role.REDACTOR).build());
        CommunityUser communityUserWRONGUSER = communityUserRepository.save(CommunityUser.builder().communities(Collections.singletonList(communityNOTUSER)).user(user1).role(Role.ADMIN).build());
        communityOK.setWorkers(Collections.singletonList(communityUserOK));
        communityNOTADMIN.setWorkers(Collections.singletonList(communityUserWRONGROLE));
        communityNOTUSER.setWorkers(Collections.singletonList(communityUserWRONGUSER));
        //ACT
        List<Community> communities = communityRepository.findCommunitiesWhereUserIsAdmin(user.getId());
        //ASSERT
        assertThat(communities).contains(communityOK).doesNotContain(communityNOTADMIN, communityNOTUSER);
    }

    @Test
    void test_findCommunitiesByCreator() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        User user1 = userRepository.save(User.builder().build());
        Community communityOK = communityRepository.save(Community.builder().creator(user).build());
        Community communityNOTCREATOR = communityRepository.save(Community.builder().creator(user1).build());
        //ACT
        List<Community> communities = communityRepository.findCommunitiesByCreator(user);
        //ASSERT
        assertThat(communities).contains(communityOK).doesNotContain(communityNOTCREATOR);
    }
}
