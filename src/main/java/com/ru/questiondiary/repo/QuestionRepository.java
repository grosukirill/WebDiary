package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> getAllByCategories(String category);

    Page<Question> findAll(Pageable pageable);
}
