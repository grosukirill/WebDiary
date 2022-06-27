package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query(nativeQuery = true, value = "select * from public.community " +
            "where id in " +
            "(select following_communities_id from public.community_followers where followers_id=:userId) " +
            "order by id desc limit 4")
    List<Community> findLastFourSubscriptions(@Param("userId") Long id);

    @Query(nativeQuery = true, value = "select community.id, community.avatar, community.title, community.description, community.link from public.community" +
            "    join community_user_communities as cuc on community.id = cuc.communities_id" +
            "    join community_user as cu on cuc.workers_id = cu.id" +
            "    where cu.role = 0 and cu.user_id=:userId")
    List<Community> findCommunitiesWhereUserIsAdmin(@Param("userId") Long id);

    List<Community> findCommunitiesByCreator(User user);
}
