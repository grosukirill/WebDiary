package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String question;
    private LocalDate creationDate;
    private Boolean isAdmins;
    private Boolean isApproved;
    private Long views = 0L;

    @ManyToMany(mappedBy = "questions")
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = true, updatable = false)
    private User creator;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "community_id", nullable = true, updatable = false)
    private Community createdBy;

    public Integer getCountOfVotes() {
        return getVotes().stream().map(Vote::getVote).mapToInt(Integer::intValue).sum();
    }

    public Integer getCountOfAnswers() {
        return getAnswers().size();
    }
}
