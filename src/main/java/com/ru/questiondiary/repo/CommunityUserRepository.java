package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.CommunityUser;
import com.ru.questiondiary.web.entity.Role;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long> {
    Optional<CommunityUser> findByRoleAndUser(Role role, User user);

    Optional<CommunityUser> findByUserAndCommunities(User user, Community community);
}
