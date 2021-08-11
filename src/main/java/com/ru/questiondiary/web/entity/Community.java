package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    private String link;

    @Lob
    private byte[] avatar;

    @ManyToMany
    private List<User> followers = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Question> questions = new ArrayList<>();

    @ManyToMany(mappedBy = "communities")
    private List<CommunityUser> workers = new ArrayList<>();

    public void follow(User user) {
        List<User> followers = this.followers;
        followers.add(user);
        this.setFollowers(followers);
    }
}
