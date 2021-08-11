package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.*;
import com.ru.questiondiary.repo.AnswerRepository;
import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.repo.VoteRepository;
import com.ru.questiondiary.web.dto.PaginationDto;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateQuestionRequest;
import com.ru.questiondiary.web.dto.request.UpdateQuestionRequest;
import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;


    @Override
    @Transactional
    public PaginationDto findAllQuestions(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 20);
        Page<Question> questions = questionRepository.findAll(page);
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVote(1);
        List<Long> downVoted = voteRepository.getAllByVote(-1);
        for (Question question: questions.getContent()) {
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false));
            } else {
                questionDtos.add(QuestionDto.from(question, null));
            }
        }
        Collections.shuffle(questionDtos);
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
    }

    @Override
    @Transactional
    public QuestionDto findQuestionById(Long questionId, String token) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException(String.format("Question with ID [%s] not found", questionId));
        }
        User user = getUserFromToken(token);
        List<Answer> answers = answerRepository.getAllByQuestionAndUser(question.get(), user);
        return QuestionDto.fromWithAnswers(question.get(), answers);
    }

    @Override
    @Transactional
    public List<QuestionDto> findAllQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.getAllByCategories(category);
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVote(1);
        List<Long> downVoted = voteRepository.getAllByVote(-1);
        for (Question question: questions) {
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false));
            } else {
                questionDtos.add(QuestionDto.from(question, null));
            }
        }
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    @Override
    @Transactional
    public QuestionDto createQuestion(CreateQuestionRequest request, String rawToken) {
        Optional<Question> existingQuestion = questionRepository.findByQuestion(request.getQuestion());
        if (existingQuestion.isPresent()) {
            throw new DuplicateQuestionException("This question already exists");
        }
        User user = getUserFromToken(rawToken);
        LocalDate now = LocalDate.now(ZoneId.of("UTC+3"));
        Question question = new Question();
        question.setQuestion(request.getQuestion());
        question.setCreator(user);
        question.setCreationDate(now);
        question.setAnswers(new ArrayList<>());
        question.setCategories(new ArrayList<>());
        question.setComments(new ArrayList<>());
        question.setVotes(new ArrayList<>());
        questionRepository.save(question);
        return QuestionDto.from(question, null);
    }

    @Override
    public QuestionDto updateQuestion(Long questionId, UpdateQuestionRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(questionId, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionUpdateException("You are trying to update other people's question");
        }
        question.get().setQuestion(request.getNewQuestion());
        questionRepository.save(question.get());
        return QuestionDto.from(question.get(), null);
    }

    @Override
    public void deleteQuestion(Long id, String rawToken) {
        getUserFromToken(rawToken);
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(id, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionDeleteException("You are trying to delete other people's question");
        }
        questionRepository.deleteById(id);
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
