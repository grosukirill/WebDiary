package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> getAllByQuestionAndUser(Question question, User user);

    void deleteAllByQuestion(Question question);
}
