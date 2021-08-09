package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> getAllByQuestionAndUser(Question question, User user);
}
