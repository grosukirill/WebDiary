package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Country;
import com.ru.questiondiary.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto extends ResponseData {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private Integer followers;
    private Integer following;
    private Boolean isFollowed;
    private String description;
    private String shortDescription;
    private Country country;
    private String city;
    private List<CommunityDto> createdCommunities;

    public static UserDto from(User user) {
        UserDto result = new UserDto();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.avatar = user.getAvatar();
        result.followers = user.getFollowers().size();
        result.following = user.getFollowing().size();
        result.description = user.getDescription();
        result.shortDescription = user.getShortDescription();
        result.country = user.getCountry();
        result.city = user.getCity();
        return result;
    }

    public static UserDto from(User user, Boolean isFollowed) {
        UserDto result = new UserDto();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.avatar = user.getAvatar();
        result.followers = user.getFollowers().size();
        result.following = user.getFollowing().size();
        result.description = user.getDescription();
        result.shortDescription = user.getShortDescription();
        result.country = user.getCountry();
        result.city = user.getCity();
        result.isFollowed = isFollowed;
        return result;
    }

    public static UserDto from(User user, Boolean isFollowed, List<CommunityDto> createdCommunities) {
        UserDto result = new UserDto();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.avatar = user.getAvatar();
        result.followers = user.getFollowers().size();
        result.following = user.getFollowing().size();
        result.description = user.getDescription();
        result.shortDescription = user.getShortDescription();
        result.country = user.getCountry();
        result.city = user.getCity();
        result.isFollowed = isFollowed;
        result.createdCommunities = createdCommunities;
        return result;
    }
}
