package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void test_create() {
        //ARRANGE
        User someUser =  User.builder().firstName("Some Name").build();
        //ACT
        User savedUser = userRepository.save(someUser);
        //ASSERT
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getFirstName().equals("Some Name")).isTrue();
    }

    @Test
    void test_read() {
        //ARRANGE
        User someUser = User.builder().build();
        User saved = userRepository.save(someUser);
        //ACT
        boolean existsById = userRepository.existsById(saved.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        User someUser = User.builder().build();
        User saved = userRepository.save(someUser);
        //ACT
        saved.setFirstName("Some Name");
        User updatedUser = userRepository.save(saved);
        //ASSERT
        assertThat(updatedUser.getFirstName().equals("Some Name"));
    }

    @Test
    void test_delete() {
        //ARRANGE
        User someUser = User.builder().build();
        User saved = userRepository.save(someUser);
        //ACT
        userRepository.deleteById(saved.getId());
        //ASSERT
        assertThat(userRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    void test_findByEmail() {
        //ARRANGE
        User user = User.builder().email("test@gmail.com").build();
        userRepository.save(user);
        //ACT
        Optional<User> found = userRepository.findByEmail("test@gmail.com");
        //ASSERT
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail().equals("test@gmail.com"));
    }

    @Test
    void test_findByEmail_notFound() {
        //ARRANGE
        User user = User.builder().email("bad@gmail.com").build();
        userRepository.save(user);
        //ACT
        Optional<User> found = userRepository.findByEmail("test@gmail.com");
        //ASSERT
        assertThat(found.isPresent()).isFalse();
    }
}
