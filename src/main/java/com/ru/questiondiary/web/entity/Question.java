package com.ru.questiondiary.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String question;
    private LocalDate creationDate;

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "category")
    private List<String> categories;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @OneToMany(mappedBy = "answer")
    private List<Answer> answers;
}
