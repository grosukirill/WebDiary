package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Answer;
import com.ru.questiondiary.web.entity.Comment;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.Vote;
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
    private UserDto creator;
    private List<AnswerDto> answers;
    private Integer votes;
    private List<CommentDto> comments;

    public static QuestionDto fromWithComments(Question question, List<Comment> comments) {
        List<CommentDto> commentsDtos = new ArrayList<>();
        List<AnswerDto> answerDtos = new ArrayList<>();
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
        result.setCreator(UserDto.from(question.getCreator()));
        result.setVotes(question.getCountOfVotes());
        result.setAnswers(answerDtos);
        result.setComments(commentsDtos);
        return result;
    }

    public static QuestionDto fromWithVotes(Question question, List<Vote> votes) {
        int countVotes = votes.stream().map(Vote::getVote).mapToInt(Integer::intValue).sum();
        List<AnswerDto> answerDtos = new ArrayList<>();
        List<CommentDto> commentDtos = new ArrayList<>();
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
        result.setCreator(UserDto.from(question.getCreator()));
        result.setAnswers(answerDtos);
        result.setVotes(question.getCountOfVotes());
        result.setComments(commentDtos);
        result.setVotes(countVotes);
        return result;
    }

    public static QuestionDto fromWithAnswers(Question question, List<Answer> answers) {
        List<AnswerDto> answerDtos = new ArrayList<>();
        List<CommentDto> comments = new ArrayList<>();
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
        result.setCreator(UserDto.from(question.getCreator()));
        result.setAnswers(answerDtos);
        result.setVotes(question.getCountOfVotes());
        result.setComments(comments);
        return result;
    }

    public static QuestionDto from(Question question) {
        List<CommentDto> comments = new ArrayList<>();
        for (Comment comment: question.getComments()) {
            comments.add(CommentDto.from(comment));
        }
        QuestionDto result = new QuestionDto();
        result.setId(question.getId());
        result.setQuestion(question.getQuestion());
        result.setCreationDate(question.getCreationDate());
        result.setCreator(UserDto.from(question.getCreator()));
        result.setVotes(question.getCountOfVotes());
        result.setComments(comments);
        return result;
    }
}
