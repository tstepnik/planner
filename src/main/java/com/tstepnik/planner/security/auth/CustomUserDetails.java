package com.tstepnik.planner.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private com.tstepnik.planner.domain.User user;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, com.tstepnik.planner.domain.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public com.tstepnik.planner.domain.User getUser() {
        return user;
    }
}
