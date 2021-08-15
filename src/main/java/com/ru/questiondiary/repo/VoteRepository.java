package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> getAllByQuestionAndUserAndVote(Question question, User user, Integer vote);

    Boolean existsByQuestionAndUserAndVoteNot(Question question, User user, Integer vote);

    Vote getByQuestionAndUserAndVote(Question question, User user, Integer vote);

    List<Vote> getAllByQuestion(Question question);

    @Query(nativeQuery = true, value = "select question_id from public.vote where vote=:vote and users_id=:userId")
    List<Long> getAllByVoteAndUser(@Param("vote") Integer vote, @Param("userId") Long userId);

    void deleteAllByQuestion(Question question);
}
