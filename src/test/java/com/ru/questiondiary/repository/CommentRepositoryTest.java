package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.CommentRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.Comment;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void test_create() {
        //ARRANGE
        Question question = questionRepository.save(Question.builder().build());
        User user = userRepository.save(User.builder().build());
        Comment comment = Comment.builder().question(question).user(user).build();
        //ACT
        Comment saved = commentRepository.save(comment);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        Question question = questionRepository.save(Question.builder().build());
        User user = userRepository.save(User.builder().build());
        Comment comment = commentRepository.save(Comment.builder().question(question).user(user).build());
        //ACT
        boolean existsById = commentRepository.existsById(comment.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        Question question = questionRepository.save(Question.builder().build());
        User user = userRepository.save(User.builder().build());
        Comment comment = Comment.builder().question(question).user(user).build();
        //ACT
        comment.setComment("Test comment");
        Comment updated = commentRepository.save(comment);
        //ASSERT
        assertThat(updated.getComment().equals("Test comment")).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        Question question = questionRepository.save(Question.builder().build());
        User user = userRepository.save(User.builder().build());
        Comment comment = commentRepository.save(Comment.builder().question(question).user(user).build());
        //ACT
        commentRepository.delete(comment);
        //ASSERT
        assertThat(commentRepository.existsById(comment.getId())).isFalse();
    }

    @Test
    void test_getAllByQuestion() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Question question2 = questionRepository.save(Question.builder().build());
        Comment comment1 = commentRepository.save(Comment.builder().question(question).user(user).build());
        Comment comment2 = commentRepository.save(Comment.builder().question(question).user(user).build());
        Comment comment3 = commentRepository.save(Comment.builder().question(question).user(user).build());
        Comment comment4 = commentRepository.save(Comment.builder().question(question).user(user).build());
        Comment comment5 = commentRepository.save(Comment.builder().question(question2).user(user).build());
        //ACT
        List<Comment> comments = commentRepository.getAllByQuestion(question);
        //ASSERT
        assertThat(comments.size() == 4).isTrue();
        assertThat(comments).contains(comment1, comment2, comment3, comment4);
        assertThat(comments).doesNotContain(comment5);
    }

}
