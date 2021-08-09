package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.QuestionNotFoundException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.CommentRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateCommentRequest;
import com.ru.questiondiary.web.entity.Comment;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public QuestionDto createComment(CreateCommentRequest request) {
        String comment = request.getComment();
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", request.getUserId()));
        } else if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", request.getQuestionId()));
        }
        Comment createdComment = Comment.builder()
                .comment(comment)
                .question(question.get())
                .user(user.get())
                .date(LocalDateTime.now())
                .build();
        commentRepository.save(createdComment);
        List<Comment> updatedComments = commentRepository.getAllByQuestion(question.get());
        return QuestionDto.fromWithComments(question.get(), updatedComments);
    }
}
