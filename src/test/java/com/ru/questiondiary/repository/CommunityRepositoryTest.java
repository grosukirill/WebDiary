package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.CommunityRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.Community;
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
}
