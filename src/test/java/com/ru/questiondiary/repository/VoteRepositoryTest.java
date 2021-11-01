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

import java.util.List;

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
    void test_getAllByQuestionAndUserAndVote_votesReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, -1);
        Vote otherVote = someVote(otherUser, someQuestion, 1);
        //ACT
        List<Vote> votes = voteRepository.getAllByQuestionAndUserAndVote(someQuestion, someUser, -1);
        //ASSERT
        assertThat(votes.size()).isGreaterThan(0);
        assertThat(votes).contains(someVote);
        assertThat(votes).doesNotContain(otherVote);
    }

    @Test
    void test_getAllByQuestionAndUserAndVote_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        Vote otherVote = someVote(otherUser, someQuestion, -1);
        //ACT
        List<Vote> votes = voteRepository.getAllByQuestionAndUserAndVote(someQuestion, someUser, -1);
        //ASSERT
        assertThat(votes).doesNotContain(someVote);
        assertThat(votes).doesNotContain(otherVote);
        assertThat(votes.size()).isEqualTo(0);
    }

    @Test
    void test_existsByQuestionAndUserAndVoteNot_true() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        someVote(someUser, someQuestion, 1);
        //ACT
        Boolean exists = voteRepository.existsByQuestionAndUserAndVoteNot(someQuestion, someUser, -1);
        //ASSERT
        assertThat(exists).isTrue();
    }

    @Test
    void test_existsByQuestionAndUserAndVoteNot_false() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        someVote(someUser, someQuestion, 1);
        //ACT
        Boolean exists = voteRepository.existsByQuestionAndUserAndVoteNot(someQuestion, someUser, 1);
        //ASSERT
        assertThat(exists).isFalse();
    }

    @Test
    void test_getByQuestionAndUserAndVote_voteReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        Vote otherVote = someVote(otherUser, someQuestion, 1);
        //ACT
        Vote vote = voteRepository.getByQuestionAndUserAndVote(someQuestion, someUser, 1);
        //ASSERT
        assertThat(vote.getId().equals(someVote.getId())).isTrue();
        assertThat(vote.getId().equals(otherVote.getId())).isFalse();
    }

    @Test
    void test_getByQuestionAndUserAndVote_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        //ACT
        Vote vote = voteRepository.getByQuestionAndUserAndVote(someQuestion, otherUser, 1);
        //ASSERT
        assertThat(vote).isNull();
    }

    @Test
    void test_getAllByQuestion_voteReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        Vote otherVote = someVote(someUser, otherQuestion, 1);
        //ACT
        List<Vote> votes = voteRepository.getAllByQuestion(someQuestion);
        //ASSERT
        assertThat(votes.size()).isGreaterThan(0);
        assertThat(votes).contains(someVote);
        assertThat(votes).doesNotContain(otherVote);
    }

    @Test
    void test_getAllByQuestion_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        //ACT
        List<Vote> votes = voteRepository.getAllByQuestion(otherQuestion);
        //ASSERT
        assertThat(votes.size()).isEqualTo(0);
    }

    @Test
    void test_getAllByVoteAndUser_idsReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        someVote(someUser, someQuestion, 1);
        someVote(otherUser, otherQuestion, 1);
        //ACT
        List<Long> questionIds = voteRepository.getAllByVoteAndUser(1, someUser.getId());
        //ASSERT
        assertThat(questionIds.size()).isGreaterThan(0);
        assertThat(questionIds).contains(someQuestion.getId());
        assertThat(questionIds).doesNotContain(otherQuestion.getId());
    }

    @Test
    void test_getAllByVoteAndUser_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        User thirdUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        someVote(someUser, someQuestion, 1);
        someVote(otherUser, otherQuestion, 1);
        //ACT
        List<Long> questionIds = voteRepository.getAllByVoteAndUser(1, thirdUser.getId());
        //ASSERT
        assertThat(questionIds.size()).isEqualTo(0);
    }

    @Test
    void test_deleteAllByQuestion() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        Vote someVote = someVote(someUser, someQuestion, 1);
        Vote otherVote = someVote(otherUser, someQuestion, 1);
        Vote thirdVote = someVote(someUser, otherQuestion, 1);
        Vote fourthVote = someVote(otherUser, otherQuestion, 1);
        //ACT
        voteRepository.deleteAllByQuestion(otherQuestion);
        boolean someVoteExists = voteRepository.existsById(someVote.getId());
        boolean otherVoteExists = voteRepository.existsById(otherVote.getId());
        boolean thirdVoteExists = voteRepository.existsById(thirdVote.getId());
        boolean fourthVoteExists = voteRepository.existsById(fourthVote.getId());
        //ASSERT
        assertThat(someVoteExists).isTrue();
        assertThat(otherVoteExists).isTrue();
        assertThat(thirdVoteExists).isFalse();
        assertThat(fourthVoteExists).isFalse();
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
