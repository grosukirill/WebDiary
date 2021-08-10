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
@Table(name = "community_user")
public class CommunityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Role role;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Community> communities = new ArrayList<>();
}
