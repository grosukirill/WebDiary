package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.DuplicateVoteException;
import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;


    @Override
    public QuestionDto createVote(CreateVoteRequest request) {
        Integer vote = request.getVote();
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", request.getUserId()));
        } else if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", request.getQuestionId()));
        } else if (checkForRepeatedVote(question.get(), user.get())) {
            throw new DuplicateVoteException("This user already voted this question");
        }
        Vote createdVote = Vote.builder()
                .vote(vote)
                .question(question.get())
                .user(user.get())
                .build();
        voteRepository.save(createdVote);
        return QuestionDto.from(question.get());
    }

    private boolean checkForRepeatedVote(Question question, User user) {
        List<Vote> votes = voteRepository.getAllByQuestionAndUser(question, user);
        return !votes.isEmpty();
    }
}
