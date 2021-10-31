package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.repo.VoteRepository;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class VoteRepositoryTest {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void test_save() {
        //ARRANGE
        Question someQuestion = someQuestion();
        User someUser = someUser();
        Vote someVote = Vote.builder().user(someUser).question(someQuestion).vote(1).build();
        //ACT
        Vote saved = voteRepository.save(someVote);
        //ASSERT
        assertThat(voteRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        Question someQuestion = someQuestion();
        User someUser = someUser();
        Vote someVote = someVote(someUser, someQuestion, 1);
        //ACT
        someVote.setVote(-1);
        Vote updated = voteRepository.save(someVote);
        //ASSERT
        assertThat(updated.getVote().equals(-1)).isTrue();
    }

    @Test
    void test_read() {
        //ARRANGE
        Question someQuestion = someQuestion();
        User someUser = someUser();
        Vote someVote = someVote(someUser, someQuestion, 1);
        //ACT
        boolean exists = voteRepository.existsById(someVote.getId());
        //ASSERT
        assertThat(exists).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        Question someQuestion = someQuestion();
        User someUser = someUser();
        Vote someVote = someVote(someUser, someQuestion, 1);
        //ACT
        voteRepository.delete(someVote);
        //ASSERT
        assertThat(voteRepository.existsById(someVote.getId())).isFalse();
    }

    @Test
    void test_getAllByQuestionAndUserAndVote_questionsReturned() {

    }

    private Vote someVote(User user, Question question, Integer voteNumber) {
        return voteRepository.save(Vote.builder()
                .user(user)
                .question(question)
                .vote(voteNumber)
                .build());
    }

    private User someUser() {
        return userRepository.save(User.builder()
                .email("test@gmail.com")
                .build());
    }

    private Question someQuestion() {
        return questionRepository.save(Question.builder()
                .question("Test?")
                .build());
    }
}
