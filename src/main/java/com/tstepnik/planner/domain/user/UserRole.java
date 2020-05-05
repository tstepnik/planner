package com.tstepnik.planner.domain.user;

import com.tstepnik.planner.domain.user.Role;

import javax.persistence.*;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole(){}

    public UserRole(Role role ){
        this.role=role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
