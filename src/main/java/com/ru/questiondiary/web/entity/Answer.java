package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Lob
    String answer;
    String date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    Question question;
}
