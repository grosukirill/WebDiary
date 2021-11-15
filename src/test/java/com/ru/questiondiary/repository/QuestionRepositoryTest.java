package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.*;
import com.ru.questiondiary.web.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void test_create() {
        //ARRANGE
        User someUser = someUser();
        //ACT
        Question saved = questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .creator(someUser)
                .build());
        //ASSERT
        assertThat(questionRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    void test_read() {
        //ARRANGE
        Question someQuestion = someQuestion();
        //ACT
        boolean exists = questionRepository.existsById(someQuestion.getId());
        //ASSERT
        assertThat(exists).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        Question someQuestion = someQuestion();
        //ACT
        someQuestion.setQuestion("Not Test?");
        Question updated = questionRepository.save(someQuestion);
        //ASSERT
        assertThat(updated.getQuestion().equals("Not Test?")).isTrue();
    }

    @Test
    void test_delete() {
        //ARRANGE
        Question someQuestion = someQuestion();
        //ACT
        questionRepository.delete(someQuestion);
        //ASSERT
        assertThat(questionRepository.existsById(someQuestion.getId())).isFalse();
    }

    @Test
    void test_findAll_questionsReturned() {
        //ARRANGE
        Question someQuestion = someQuestion();
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAll(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
    }

    @Test
    void test_findAll_nothingReturned() {
        //ARRANGE
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAll(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findByQuestion_questionReturned() {
        //ARRANGE
        Question someQuestion = someQuestion();
        //ACT
        Optional<Question> question = questionRepository.findByQuestion("Test?");
        //ASSERT
        assertThat(question.isEmpty()).isFalse();
        assertThat(question.get().getId().equals(someQuestion.getId())).isTrue();
    }

    @Test
    void test_findByQuestion_nothingReturned() {
        //ARRANGE
        someQuestion();
        //ACT
        Optional<Question> question = questionRepository.findByQuestion("Bad question");
        //ASSERT
        assertThat(question.isPresent()).isFalse();
    }

    @Test
    void test_findByIdAndCreator_questionReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion(someUser);
        //ACT
        Optional<Question> question = questionRepository.findByIdAndCreator(someQuestion.getId(), someUser);
        //ASSERT
        assertThat(question.isPresent()).isTrue();
        assertThat(question.get().getId().equals(someQuestion.getId())).isTrue();
        assertThat(question.get().getCreator().getId().equals(someUser.getId())).isTrue();
    }

    @Test
    void test_findByIdAndCreator_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion(someUser);
        //ACT
        Optional<Question> question = questionRepository.findByIdAndCreator(someQuestion.getId(), otherUser);
        //ASSERT
        assertThat(question.isPresent()).isFalse();
    }

    @Test
    void test_deleteAllCreatedBy() {
        //ARRANGE
        Community someCommunity = someCommunity();
        Question someQuestion = someQuestion(someCommunity);
        //ACT
        questionRepository.deleteAllByCreatedBy(someCommunity);
        boolean exists = questionRepository.existsById(someQuestion.getId());
        //ASSERT
        assertThat(exists).isFalse();
    }

    @Test
    void test_findAllByIsAdminsTrue_questionReturned() {
        //ARRANGE
        Question someQuestion = someQuestion(true);
        Question otherQuestion = someQuestion(false);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByIsAdminsTrue(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
    }

    @Test
    void test_findAllByAdminsTrue_nothingReturned() {
        //ARRANGE
        someQuestion(false);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByIsAdminsTrue(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findRecommendations_questionsReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser("other@gmail.com");
        otherUser.setIsApproved(true);
        User thirdUser = someUser("third@gmail.com");
        thirdUser.setIsApproved(true);
        List<User> following = new ArrayList<>();
        following.add(otherUser);
        someUser.setFollowing(following);
        Question someQuestion = someQuestion(thirdUser);
        Question otherQuestion = someQuestion(otherUser);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findRecommendations(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
    }

    @Test
    void test_findRecommendations_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findRecommendations(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findRecommendations_nothingReturned_usersAreNotApproved() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser("other@gmail.com");
        otherUser.setIsApproved(false);
        User thirdUser = someUser("third@gmail.com");
        thirdUser.setIsApproved(false);
        Question someQuestion = someQuestion(thirdUser);
        Question otherQuestion = someQuestion(otherUser);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findRecommendations(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getContent()).doesNotContain(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findRecommendations_nothingReturned_userIsFollowingThem() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser("other@gmail.com");
        otherUser.setIsApproved(true);
        User thirdUser = someUser("third@gmail.com");
        thirdUser.setIsApproved(true);
        List<User> following = new ArrayList<>();
        following.add(otherUser);
        following.add(thirdUser);
        someUser.setFollowing(following);
        Question someQuestion = someQuestion(thirdUser);
        Question otherQuestion = someQuestion(otherUser);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findRecommendations(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getContent()).doesNotContain(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findAllByAnswerDate_questionsReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Answer someAnswer = someAnswer(someUser, someQuestion, date);
        List<Answer> answers = new ArrayList<>();
        answers.add(someAnswer);
        someQuestion.setAnswers(answers);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByAnswerDate(someUser.getId(), date, page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent().get(0).getAnswers()).contains(someAnswer);
    }

    @Test
    void test_findAllByAnswerDate_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        String date = LocalDate.of(1900, 12, 12).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        someAnswer(someUser, someQuestion, date);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByAnswerDate(someUser.getId(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findAllFavorite_questionsReturned() {
        //ARRANGE
        User someUser = someUser();
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        List<Favorite> favorites = new ArrayList<>();
        Favorite someFavorite = someFavorite(someUser, someQuestion);
        favorites.add(someFavorite);
        someUser.setFavoriteQuestions(favorites);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllFavorite(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
    }

    @Test
    void test_findAllFavorite_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        someQuestion();
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllFavorite(someUser.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findLatest_questionsReturned() {
        //ARRANGE
        LocalDate now = LocalDate.now();
        LocalDate past = LocalDate.of(1900, 12, 12);
        Question someQuestion = someQuestion(now);
        Question otherQuestion = someQuestion(past);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findLatest(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).containsExactlyInAnyOrder(someQuestion, otherQuestion);
    }

    @Test
    void test_findLatest_nothingReturned() {
        //ARRANGE
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findLatest(page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findAllByQuestionContainingIgnoreCase_questionsReturned() {
        //ARRANGE
        Question someQuestion = someQuestion("Some question?");
        Question otherQuestion = someQuestion("Other question?");
        //ACT
        List<Question> questions = questionRepository.findAllByQuestionContainingIgnoreCase("some");
        //ASSERT
        assertThat(questions.size()).isGreaterThan(0);
        assertThat(questions).contains(someQuestion);
        assertThat(questions).doesNotContain(otherQuestion);
    }

    @Test
    void test_findAllByQuestionContainingIgnoreCase_nothingReturned() {
        //ARRANGE
        someQuestion();
        //ACT
        List<Question> questions = questionRepository.findAllByQuestionContainingIgnoreCase("bad pattern");
        //ASSERT
        assertThat(questions.size()).isEqualTo(0);
    }

    @Test
    void test_findAllByCategories_questionReturned() {
        //ARRANGE
        Question someQuestion = someQuestion();
        Question otherQuestion = someQuestion();
        Category someCategory = someCategory("Some category");
        Category otherCategory = someCategory("Other category");
        Set<Category> categories = new HashSet<>();
        categories.add(someCategory);
        someQuestion.setCategories(categories);
        List<Question> categoryQuestions = new ArrayList<>();
        categoryQuestions.add(someQuestion);
        someCategory.setQuestions(categoryQuestions);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByCategories(someCategory.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent().get(0).getCategories()).contains(someCategory);
        assertThat(questions.getContent().get(0).getCategories()).doesNotContain(otherCategory);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
    }

    @Test
    void test_findAllByCategories_nothingReturned() {
        //ARRANGE
        someQuestion();
        Category someCategory = someCategory("Some category");
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByCategories(someCategory.getId(), page);
        //ASSERT
        assertThat(questions.getTotalElements()).isEqualTo(0);
    }

    @Test
    void test_findAllByCreator_questionsReturned() {
        //ARRANGE
        User someUser = someUser();
        User otherUser = someUser();
        Question someQuestion = someQuestion(someUser);
        Question otherQuestion = someQuestion(otherUser);
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByCreator(page, someUser);
        //ASSERT
        assertThat(questions.getContent().size()).isGreaterThan(0);
        assertThat(questions.getContent()).contains(someQuestion);
        assertThat(questions.getContent()).doesNotContain(otherQuestion);
    }

    @Test
    void test_findAllByCreator_nothingReturned() {
        //ARRANGE
        User someUser = someUser();
        someQuestion();
        Pageable page = PageRequest.of(0, 20);
        //ACT
        Page<Question> questions = questionRepository.findAllByCreator(page, someUser);
        //ASSERT
        assertThat(questions.getContent().size()).isEqualTo(0);
    }

    private Question someQuestion() {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .build());
    }

    private Question someQuestion(User user) {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .creator(user)
                .build());
    }

    private Question someQuestion(Community community) {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .createdBy(community)
                .build());

    }

    private Question someQuestion(boolean isAdmins) {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .isAdmins(isAdmins)
                .build());

    }

    private Question someQuestion(LocalDate date) {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question("Test?")
                .creationDate(date)
                .build());

    }

    private Question someQuestion(String question) {
        return questionRepository.save(Question.builder()
                .creationDate(LocalDate.now())
                .question(question)
                .build());

    }

    private User someUser() {
        return userRepository.save(User.builder()
                .email("Test@test.com")
                .firstName("Test")
                .lastName("Test")
                .build());
    }

    private User someUser(String email) {
        return userRepository.save(User.builder()
                .email(email)
                .firstName("Test")
                .lastName("Test")
                .build());
    }

    private Community someCommunity() {
        return communityRepository.save(Community.builder()
                .title("Test Community")
                .build());
    }

    private Answer someAnswer(User user, Question question, String date) {
        return answerRepository.save(Answer.builder()
                .user(user)
                .question(question)
                .answer("Test answer")
                .date(date)
                .build());
    }

    private Favorite someFavorite(User user, Question question) {
        return favoriteRepository.save(Favorite.builder()
                .user(user)
                .question(question)
                .build());
    }

    private Category someCategory(String category) {
        return categoryRepository.save(Category.builder()
                .category(category)
                .build());
    }
}
