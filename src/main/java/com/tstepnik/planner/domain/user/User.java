package com.tstepnik.planner.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3)
    private String login;

    private String firstName;

    private String lastName;


    @Email
    private String email;

    @NotEmpty
    @Size(min = 3)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    public User() {
    }

    public User(String userName, String password, String email) {
        this.login = userName;
        this.password = password;
        this.email = email;
    }
}
