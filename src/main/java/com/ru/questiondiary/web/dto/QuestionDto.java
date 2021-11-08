package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto extends ResponseData {
    private Long id;
    private String question;
    private LocalDate creationDate;
    private List<CategoryDto> categories;
    private String type;
    private UserDto creator;
    private CommunityDto createdBy;
    private List<AnswerDto> answers;
    private Integer votes;
    private List<CommentDto> comments;
    private Boolean voted = null;
    private Boolean isFavorite;
    private Long views;

    public static QuestionDto fromWithComments(Question question, List<Comment> comments, Boolean isFavorite) {
        String type;
        boolean isUsers = false;
        if (question.getCreator() != null) {
            type = "User";
            isUsers = true;
        } else {
            type = "Community";
        }
        List<CommentDto> commentsDtos = new ArrayList<>();
        List<AnswerDto> answerDtos = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: question.getCategories()) {
            categoryDtos.add(CategoryDto.from(category));
        }
        for (Comment comment: comments) {
            commentsDtos.add(CommentDto.from(comment));
        }
        for (Answer answer: question.getAnswers()) {
            answerDtos.add(AnswerDto.from(answer));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setViews(question.getViews());
        result.setCategories(categoryDtos);
        result.setType(type);
        if (isUsers) {
            result.setCreator(UserDto.from(question.getCreator()));
        } else {
            result.setCreatedBy(CommunityDto.from(question.getCreatedBy()));
        }
        result.setVotes(question.getCountOfVotes());
        result.setAnswers(answerDtos);
        result.setComments(commentsDtos);
        result.setIsFavorite(isFavorite);
        return result;
    }

    public static QuestionDto fromWithVotes(Question question, List<Vote> votes, Boolean isFavorite, Boolean voted) {
        String type;
        boolean isUsers = false;
        if (question.getCreator() != null) {
            type = "User";
            isUsers = true;
        } else {
            type = "Community";
        }
        int countVotes = votes.stream().map(Vote::getVote).mapToInt(Integer::intValue).sum();
        List<AnswerDto> answerDtos = new ArrayList<>();
        List<CommentDto> commentDtos = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: question.getCategories()) {
            categoryDtos.add(CategoryDto.from(category));
        }
        for (Answer answer: question.getAnswers()) {
            answerDtos.add(AnswerDto.from(answer));
        }
        for (Comment comment: question.getComments()) {
            commentDtos.add(CommentDto.from(comment));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setViews(question.getViews());
        result.setCategories(categoryDtos);
        result.setType(type);
        if (isUsers) {
            result.setCreator(UserDto.from(question.getCreator()));
        } else {
            result.setCreatedBy(CommunityDto.from(question.getCreatedBy()));
        }
        result.setAnswers(answerDtos);
        result.setVotes(question.getCountOfVotes());
        result.setComments(commentDtos);
        result.setVotes(countVotes);
        result.setIsFavorite(isFavorite);
        result.setVoted(voted);
        return result;
    }

    public static QuestionDto fromWithAnswers(Question question, List<Answer> answers, Boolean isFavorite) {
        String type;
        boolean isUsers = false;
        if (question.getCreator() != null) {
            type = "User";
            isUsers = true;
        } else {
            type = "Community";
        }
        List<AnswerDto> answerDtos = new ArrayList<>();
        List<CommentDto> comments = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: question.getCategories()) {
            categoryDtos.add(CategoryDto.from(category));
        }
        for (Answer answer: answers) {
            answerDtos.add(AnswerDto.from(answer));
        }
        for (Comment comment: question.getComments()) {
            comments.add(CommentDto.from(comment));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setViews(question.getViews());
        result.setCategories(categoryDtos);
        result.setType(type);
        if (isUsers) {
            result.setCreator(UserDto.from(question.getCreator()));
        } else {
            result.setCreatedBy(CommunityDto.from(question.getCreatedBy()));
        }
        result.setAnswers(answerDtos);
        result.setVotes(question.getCountOfVotes());
        result.setComments(comments);
        result.setIsFavorite(isFavorite);
        return result;
    }

    public static QuestionDto from(Question question, Boolean voted, Boolean isFavorite) {
        String type;
        boolean isUsers = false;
        if (question.getCreator() != null) {
            type = "User";
            isUsers = true;
        } else {
            type = "Community";
        }
        List<CommentDto> comments = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: question.getCategories()) {
            categoryDtos.add(CategoryDto.from(category));
        }
        for (Comment comment: question.getComments()) {
            comments.add(CommentDto.from(comment));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setViews(question.getViews());
        result.setCategories(categoryDtos);
        result.setType(type);
        if (isUsers) {
            result.setCreator(UserDto.from(question.getCreator()));
        } else {
            result.setCreatedBy(CommunityDto.from(question.getCreatedBy()));
        }
        result.setVotes(question.getCountOfVotes());
        result.setComments(comments);
        result.setVoted(voted);
        result.setIsFavorite(isFavorite);
        return result;
    }
}
