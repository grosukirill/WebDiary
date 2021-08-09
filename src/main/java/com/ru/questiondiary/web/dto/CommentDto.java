package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String comment;
    private UserDto user;
    private Long questionId;
    private String date;

    public static CommentDto from(Comment comment) {
        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setComment(comment.getComment());
        result.setUser(UserDto.from(comment.getUser()));
        result.setQuestionId(comment.getQuestion().getId());
        result.setDate(comment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return result;
    }
}
