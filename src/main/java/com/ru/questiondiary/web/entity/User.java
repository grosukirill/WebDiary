package com.ru.questiondiary.web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;

    @OneToMany(mappedBy = "question")
    private List<Question> createdQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<Answer> answers = new ArrayList<>();


    public User() {

    }
}
