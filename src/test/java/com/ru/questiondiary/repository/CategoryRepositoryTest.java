package com.ru.questiondiary.repository;

import com.ru.questiondiary.repo.CategoryRepository;
import com.ru.questiondiary.web.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void test_create() {
        //ARRANGE
        Category category = Category.builder().build();
        //ACT
        Category saved = categoryRepository.save(category);
        //ASSERT
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void test_read() {
        //ARRANGE
        Category category = categoryRepository.save(Category.builder().build());
        //ACT
        boolean existsById = categoryRepository.existsById(category.getId());
        //ASSERT
        assertThat(existsById).isTrue();
    }

    @Test
    void test_update() {
        //ARRANGE
        Category category = categoryRepository.save(Category.builder().build());
        //ACT
        category.setCategory("Test");
        Category updated = categoryRepository.save(category);
        //ASSERT
        assertThat(updated.getCategory().equals("Test"));
    }

    @Test
    void test_delete() {
        //ARRANGE
        Category category = categoryRepository.save(Category.builder().build());
        //ACT
        categoryRepository.delete(category);
        //ASSERT
        assertThat(categoryRepository.existsById(category.getId())).isFalse();
    }
}
