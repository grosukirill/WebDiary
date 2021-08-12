package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.FavoriteRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.entity.Favorite;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public QuestionDto createFavorite(Long questionId, String rawToken) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
        }
        User user = getUserFromToken(rawToken);
        List<Favorite> favoriteQuestions = user.getFavoriteQuestions();
        Favorite favorite = Favorite.builder()
                .question(question.get())
                .user(user)
                .build();
        favoriteRepository.save(favorite);
        favoriteQuestions.add(favorite);
        user.setFavoriteQuestions(favoriteQuestions);
        userRepository.save(user);
        return QuestionDto.from(question.get(), null, true);
    }

    private User getUserFromToken(String rawToken) {
        Map<String, String> userData = tokenService.getUserDataFromToken(rawToken);
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userData.get("id")));
        }
        return user.get();
    }
}
