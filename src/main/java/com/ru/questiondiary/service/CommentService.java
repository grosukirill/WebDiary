package com.ru.questiondiary.service;

import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateCommentRequest;

public interface CommentService {

    QuestionDto createComment(CreateCommentRequest request);
}
