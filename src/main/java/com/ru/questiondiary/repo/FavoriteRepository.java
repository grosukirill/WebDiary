package com.ru.questiondiary.repo;

import com.ru.questiondiary.web.entity.Favorite;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Boolean existsByQuestionAndUser(Question question, User user);
}
