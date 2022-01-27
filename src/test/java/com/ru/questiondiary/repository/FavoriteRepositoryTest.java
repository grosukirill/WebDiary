package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.FavoriteRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.Favorite;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void test_create() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        Favorite favorite = Favorite.builder().user(someUser).question(someQuestion).build();
        //ACT
        Favorite saved = favoriteRepository.save(favorite);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        Favorite favorite = Favorite.builder().user(someUser).question(someQuestion).build();
        favoriteRepository.save(favorite);
        //ACT
        boolean existsById = favoriteRepository.existsById(favorite.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question question1 = questionRepository.save(Question.builder().build());
        Question question2 = questionRepository.save(Question.builder().build());
        Favorite favorite = Favorite.builder().question(question1).user(someUser).build();
        //ACT
        favorite.setQuestion(question2);
        Favorite update = favoriteRepository.save(favorite);
        //ASSERT
        assertThat(update.getQuestion().equals(question1)).isFalse();
        assertThat(update.getQuestion().equals(question2)).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        Favorite favorite = favoriteRepository.save(Favorite.builder().user(someUser).question(someQuestion).build());
        //ACT
        favoriteRepository.deleteById(favorite.getId());
        //ASSERT
        assertThat(favoriteRepository.existsById(favorite.getId())).isFalse();
    }

    @Test
    void test_existsByQuestionAndUser() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        favoriteRepository.save(Favorite.builder().user(someUser).question(someQuestion).build());
        //ACT
        Boolean exists = favoriteRepository.existsByQuestionAndUser(someQuestion, someUser);
        //ASSERT
        assertThat(exists).isTrue();
    }

    @Test
    void test_existsByQuestionAndUser_notFound() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        User someUser2 = userRepository.save(User.builder().build());
        Question someQuestion2 = questionRepository.save(Question.builder().build());
        favoriteRepository.save(Favorite.builder().user(someUser).question(someQuestion).build());
        //ACT
        Boolean exists = favoriteRepository.existsByQuestionAndUser(someQuestion2, someUser2);
        //ASSERT
        assertThat(exists).isFalse();
    }

    @Test
    void test_deleteByQuestionAndUser() {
        //ARRANGE
        User someUser = userRepository.save(User.builder().build());
        Question someQuestion = questionRepository.save(Question.builder().build());
        Favorite favorite = favoriteRepository.save(Favorite.builder().user(someUser).question(someQuestion).build());
        //ACT
        favoriteRepository.deleteByQuestionAndUser(someQuestion, someUser);
        //ASSERT
        assertThat(favoriteRepository.existsById(favorite.getId())).isFalse();
    }
}
