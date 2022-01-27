package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.AnswerRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void test_create() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Answer answer = Answer.builder().user(user).question(question).build();
        //ACT
        Answer saved = answerRepository.save(answer);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Answer answer = answerRepository.save(Answer.builder().user(user).question(question).build());
        //ACT
        boolean existsById = answerRepository.existsById(answer.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Answer answer = answerRepository.save(Answer.builder().user(user).question(question).build());
        //ACT
        answer.setAnswer("Test");
        Answer updated = answerRepository.save(answer);
        //ASSERT
        assertThat(updated.getAnswer().equals("Test")).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Answer answer = answerRepository.save(Answer.builder().user(user).question(question).build());
        //ACT
        answerRepository.delete(answer);
        //ASSERT
        assertThat(answerRepository.existsById(answer.getId())).isFalse();
    }

    @Test
    void test_getByQuestionAndUser() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Question question1 = questionRepository.save(Question.builder().build());
        Answer answer1 = answerRepository.save(Answer.builder().user(user).question(question).build());
        Answer answer2 = answerRepository.save(Answer.builder().user(user).question(question).build());
        Answer answer3 = answerRepository.save(Answer.builder().question(question1).user(user).build());
        //ACT
        List<Answer> answers = answerRepository.getAllByQuestionAndUser(question, user);
        //ASSERT
        assertThat(answers.size() == 2).isTrue();
        assertThat(answers).contains(answer1, answer2);
        assertThat(answers).doesNotContain(answer3);
    }

    @Test
    void test_deleteAllByQuestion() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Question question1 = questionRepository.save(Question.builder().build());
        Answer answer1 = answerRepository.save(Answer.builder().user(user).question(question).build());
        Answer answer2= answerRepository.save(Answer.builder().user(user).question(question1).build());
        //ACT
        answerRepository.deleteAllByQuestion(question);
        //ASSERT
        assertThat(answerRepository.existsById(answer1.getId())).isFalse();
        assertThat(answerRepository.existsById(answer2.getId())).isTrue();
    }

    @Test
    void test_getMostAnsweredQuestions() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Question question2 = questionRepository.save(Question.builder().build());
        answerRepository.save(Answer.builder().user(user).question(question).build());
        Pageable pageable = PageRequest.of(0, 5);
        //ACT
        Page<Map<String, BigInteger>> mostAnsweredQuestions = answerRepository.getMostAnsweredQuestions(user.getId(), pageable);
        //ASSERT
        assertThat(mostAnsweredQuestions.getContent().size() == 1).isTrue();
        assertThat(mostAnsweredQuestions.getContent().get(0).get("question_id").longValue() == question.getId()).isTrue();
        assertThat(mostAnsweredQuestions.getContent().get(0).get("question_id").longValue() != question2.getId()).isTrue();

    }

    @Test
    void test_findAllByUser() {
        //ARRANGE
        User user = userRepository.save(User.builder().build());
        User user2 = userRepository.save(User.builder().build());
        Question question = questionRepository.save(Question.builder().build());
        Answer answer = answerRepository.save(Answer.builder().user(user).question(question).build());
        Pageable pageable = PageRequest.of(0, 5);
        //ACT
        Page<Answer> answerPage = answerRepository.findAllByUser(pageable, user);
        //ASSERT
        assertThat(answerPage.getContent().size() == 1).isTrue();
        assertThat(answerPage.getContent().get(0).getUser().equals(user)).isTrue();
        assertThat(answerPage.getContent().get(0).getUser().equals(user2)).isFalse();
    }
}
