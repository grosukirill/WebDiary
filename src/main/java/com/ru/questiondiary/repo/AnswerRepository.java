package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> getAllByQuestionAndUser(Question question, User user);

    void deleteAllByQuestion(Question question);

    @Query(nativeQuery = true,
            value = "select question_id, count(question_id) from public.answer where user_id=:userId group by question_id order by count(question_id) desc",
            countQuery = "select count(question_id) from public.answer")
    Page<Map<String, BigInteger>> getMostAnsweredQuestions(@Param("userId") Long userId, Pageable pageable);

    Page<Answer> findAllByUser(Pageable pageable, User user);

}
