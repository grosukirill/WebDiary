package com.ru.questiondiary.service;


import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.dto.request.CreateVoteRequest;

public interface VoteService {

    QuestionDto createVote(CreateVoteRequest request, String rawToken);
}
