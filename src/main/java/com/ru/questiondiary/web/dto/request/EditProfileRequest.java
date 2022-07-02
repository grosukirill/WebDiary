package com.ru.questiondiary.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.questiondiary.web.entity.Country;
import com.ru.questiondiary.web.entity.Link;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EditProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private String shortDescription;
    private Country country;
    private String city;
    private List<Link> links = new ArrayList<>();

    @JsonCreator
    public EditProfileRequest(@JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName,
                              @JsonProperty("email") String email,
                              @JsonProperty("description") String description,
                              @JsonProperty("shortDescription") String shortDescription,
                              @JsonProperty("country") String country,
                              @JsonProperty("city") String city,
                              @JsonProperty("links") List<String> socials) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.shortDescription = shortDescription;
        this.country = Country.valueOf(country.toUpperCase());
        this.city = city;

        List<Link> links = new ArrayList<>();
        for (String link: socials) {
            links.add(Link.builder().link(link).build());
        }

        this.links = links;
    }
}
