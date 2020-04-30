package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.security.auth.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public User getLoggedUser() {
        CustomUserDetails userDetails = (CustomUserDetails) ((UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return userDetails.getUser();
    }
}
