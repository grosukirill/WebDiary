package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> getAllByCategories(String category);

    Page<Question> findAll(Pageable pageable);

    Optional<Question> findByQuestion(String question);

    Optional<Question> findByIdAndCreator(Long questionId, User user);

    void deleteAllByCreatedBy(Community community);

    @Query(nativeQuery = true, value = "select * from public.question where is_admins=true")
    Page<Question> findAllByIsAdminsTrue(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from question where is_admins=false and community_id in (select following_communities_id from public.community_followers where followers_id=:followedId) or creator_id in (select follower_id from public.user_relations where followed_id=:followedId)")
    Page<Question> findAllByIsAdminsFalse(@Param("followedId") Long followedId, Pageable pageable);
}
