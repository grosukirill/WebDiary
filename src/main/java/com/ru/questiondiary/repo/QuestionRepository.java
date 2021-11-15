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
    @Query(nativeQuery = true, value = "select * from public.question where id in (select questions_id from public.categories_questions where categories_id=:categoryId)")
    Page<Question> findAllByCategories(@Param("categoryId") Long categoryId, Pageable pageable);

    Page<Question> findAll(Pageable pageable);

    Optional<Question> findByQuestion(String question);

    Optional<Question> findByIdAndCreator(Long questionId, User user);

    void deleteAllByCreatedBy(Community community);

    @Query(nativeQuery = true, value = "select * from public.question where is_admins=true")
    Page<Question> findAllByIsAdminsTrue(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from public.question where creator_id in (select id from public.users where is_approved=true) and creator_id not in (select follower_id from public.user_relations where followed_id=:followedId) order by random()")
    Page<Question> findRecommendations(@Param("followedId") Long followedId, Pageable page);

    @Query(nativeQuery = true, value = "select * from public.question where id in (select question_id from public.answer where date=:date and user_id=:userId)")
    Page<Question> findAllByAnswerDate(@Param("userId") Long userId, @Param("date") String date, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from public.question where id in (select question_id from public.favorite where users_id=:userId)")
    Page<Question> findAllFavorite(@Param("userId") Long userId, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from public.question")
    Page<Question> findLatest(Pageable page);

    List<Question> findAllByQuestionContainingIgnoreCase(String pattern);

    Page<Question> findAllByCreator(Pageable pageable, User user);

    @Query(nativeQuery = true, value = "select * from public.question where creator_id in (select followed_id from public.user_relations where follower_id=:userId)")
    Page<Question> findFollowersFeed(@Param("userId") Long userId, Pageable page);

    @Query(nativeQuery = true, value = "select question.*, coalesce(sum(vote.vote), 0) as total from question left join vote on question.id = vote.question_id where question.creation_date between current_date - (interval '7' day) and current_date group by question.id order by total desc offset :from fetch first 20 rows only ")
    List<Question> findTopByVotesAndCreationDateLastWeek(@Param("from") int from);
}
