package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.CommunityUser;
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
    private Integer followers;
    private Integer questions;

    public static CommunityDto from(Community community) {
        List<CommunityUserDto> workers = new ArrayList<>();
        for (CommunityUser worker: community.getWorkers()) {
            workers.add(CommunityUserDto.from(worker));
        }
        CommunityDto result = new CommunityDto();
        result.setId(community.getId());
        result.setTitle(community.getTitle());
        result.setDescription(community.getDescription());
        result.setLink(community.getLink());
        result.setFollowers(community.getFollowers().size());
        result.setQuestions(community.getQuestions().size());
        result.setWorkers(workers);
        return result;
    }
}
