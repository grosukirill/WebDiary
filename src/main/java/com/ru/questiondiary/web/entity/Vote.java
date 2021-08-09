package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer vote;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "users_id", nullable = false, updatable = false)
    private User user;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    private Question question;

    protected Vote() {}
}
