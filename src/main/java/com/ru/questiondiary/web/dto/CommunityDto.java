package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.CommunityUser;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommunityDto extends ResponseData {
    private Long id;
    private String title;
    private String description;
    private String link;
    private List<CommunityUserDto> workers;
    private List<UserDto> followers;
    private List<QuestionDto> questions;

    public static CommunityDto from(Community community) {
        List<UserDto> followers = new ArrayList<>();
        List<QuestionDto> questions = new ArrayList<>();
        List<CommunityUserDto> workers = new ArrayList<>();
        for (User user: community.getFollowers()) {
            followers.add(UserDto.from(user));
        }
        for (Question question: community.getQuestions()) {
            questions.add(QuestionDto.from(question, null));
        }
        for (CommunityUser worker: community.getWorkers()) {
            workers.add(CommunityUserDto.from(worker));
        }
        CommunityDto result = new CommunityDto();
        result.setId(community.getId());
        result.setTitle(community.getTitle());
        result.setDescription(community.getDescription());
        result.setLink(community.getLink());
        result.setFollowers(followers);
        result.setQuestions(questions);
        result.setWorkers(workers);
        return result;
    }
}
