package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.*;
import com.ru.questiondiary.repo.*;
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
import org.springframework.data.domain.Sort;
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
    private final FavoriteRepository favoriteRepository;


    @Override
    @Transactional
    public PaginationDto findAllQuestions(Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20);
        Page<Question> questions = questionRepository.findAll(page);
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVoteAndUser(1, user.getId());
        List<Long> downVoted = voteRepository.getAllByVoteAndUser(-1, user.getId());
        for (Question question: questions.getContent()) {
            Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question, user);
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true, isFavorite));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false, isFavorite));
            } else {
                questionDtos.add(QuestionDto.from(question, null, isFavorite));
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
        Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question.get(), user);
        return QuestionDto.fromWithAnswers(question.get(), answers, isFavorite);
    }

    @Override
    @Transactional
    public List<QuestionDto> findAllQuestionsByCategory(String category, String rawToken) {
        User user = getUserFromToken(rawToken);
        List<Question> questions = questionRepository.getAllByCategories(category);
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVoteAndUser(1, user.getId());
        List<Long> downVoted = voteRepository.getAllByVoteAndUser(-1, user.getId());
        for (Question question: questions) {
            Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question, user);
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true, isFavorite));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false, isFavorite));
            } else {
                questionDtos.add(QuestionDto.from(question, null, isFavorite));
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
        return QuestionDto.from(question, null, null);
    }

    @Override
    @Transactional
    public QuestionDto updateQuestion(Long questionId, UpdateQuestionRequest request, String rawToken) {
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(questionId, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionUpdateException("You are trying to update other people's question");
        }
        question.get().setQuestion(request.getNewQuestion());
        questionRepository.save(question.get());
        Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question.get(), user);
        return QuestionDto.from(question.get(), null, isFavorite);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id, String rawToken) {
        getUserFromToken(rawToken);
        User user = getUserFromToken(rawToken);
        Optional<Question> question = questionRepository.findByIdAndCreator(id, user);
        if (question.isEmpty()) {
            throw new ForeignQuestionDeleteException("You are trying to delete other people's question");
        }
        answerRepository.deleteAllByQuestion(question.get());
        voteRepository.deleteAllByQuestion(question.get());
        questionRepository.delete(question.get());
    }

    @Override
    @Transactional
    public PaginationDto findFeed(String type, Integer pageNumber, String rawToken) {
        User user = getUserFromToken(rawToken);
        Pageable page = PageRequest.of(pageNumber, 20, Sort.by("creation_date").descending());
        Page<Question> questions;
        if (type.equals("Admin")) {
            questions = questionRepository.findAllByIsAdminsTrue(page);
        } else if (type.equals("Users")) {
            questions = questionRepository.findAllByIsAdminsFalse(user.getId(), page);
        } else {
            throw new WrongFeedTypeException(String.format("Feed type %s does not match any known types.", type));
        }
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Long> upVoted = voteRepository.getAllByVoteAndUser(1, user.getId());
        List<Long> downVoted = voteRepository.getAllByVoteAndUser(-1, user.getId());
        for (Question question: questions.getContent()) {
            Boolean isFavorite = favoriteRepository.existsByQuestionAndUser(question, user);
            if (upVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, true, isFavorite));
            } else if (downVoted.contains(question.getId())) {
                questionDtos.add(QuestionDto.from(question, false, isFavorite));
            } else {
                questionDtos.add(QuestionDto.from(question, null, isFavorite));
            }
        }
        return new PaginationDto(questionDtos, questions.hasNext(), questions.getNumber()+1);
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
