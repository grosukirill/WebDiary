package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "category")
    private List<String> categories;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Vote> votes = new ArrayList<>();

    public Integer getCountOfVotes() {
        return getVotes().stream().map(Vote::getVote).mapToInt(Integer::intValue).sum();
    }
}
