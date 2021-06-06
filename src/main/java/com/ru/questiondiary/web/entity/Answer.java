package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    String answer;
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    Question question;
}
