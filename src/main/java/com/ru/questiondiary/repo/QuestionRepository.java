package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> getAllByCategories(String category);

    Page<Question> findAll(Pageable pageable);

    Optional<Question> findByQuestion(String question);

    Optional<Question> findByIdAndCreator(Long questionId, User user);

    void deleteAllByCreatedBy(Community community);
}
