package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.DuplicateVoteException;
import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.FavoriteRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.repo.VoteRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateVoteRequest;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final TokenService tokenService;


    @Override
    @Transactional
    public QuestionDto createVote(CreateVoteRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Integer vote = request.getVote();
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", request.getQuestionId()));
        } else if (checkForRepeatedVote(question.get(), user)) {
            throw new DuplicateVoteException("This user already voted this question");
        }
        Vote createdVote = Vote.builder()
                .vote(vote)
                .question(question.get())
                .user(user)
                .build();
        voteRepository.save(createdVote);
        List<Vote> updatedVotes = voteRepository.getAllByQuestion(question.get());
        Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question.get(), user);
        return QuestionDto.fromWithVotes(question.get(), updatedVotes, isFavorite);
    }

    private boolean checkForRepeatedVote(Question question, User user) {
        List<Vote> votes = voteRepository.getAllByQuestionAndUser(question, user);
        return !votes.isEmpty();
    }

    private User getUserFromToken(String token) {
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Token [%s] has no linked user", token));
        }
        return user.get();
    }
}
