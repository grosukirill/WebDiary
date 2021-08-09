package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Comment;
import com.ru.questiondiary.web.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByQuestion(Question question);
}
