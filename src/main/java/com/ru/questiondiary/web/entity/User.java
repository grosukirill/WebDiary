package com.ru.questiondiary.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String avatar;
    private Boolean isApproved;

    @OneToMany(mappedBy = "creator")
    private List<Question> createdQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<Community> followingCommunities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_RELATIONS",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> following;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> followers;

    @OneToMany(mappedBy = "question")
    private List<Favorite> favoriteQuestions = new ArrayList<>();


    public User() {

    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User))
            return false;

        User other = (User) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void followOtherUser(User user) {
        List<User> following = this.following;
        following.add(user);
        this.following = following;
    }

    public void addFollower(User user) {
        List<User> followers = this.followers;
        followers.add(user);
        this.followers = followers;
    }
}
