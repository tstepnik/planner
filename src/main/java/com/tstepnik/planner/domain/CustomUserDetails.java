package com.tstepnik.planner.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetail extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;
    private Long id;


    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities,Long id) {
        super(username, password, authorities);
        this.id=id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}