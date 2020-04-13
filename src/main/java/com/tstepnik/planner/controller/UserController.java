package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers(){return userRepository.findAll();}

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getLoggedUser(Principal principal){
        String login = principal.getName();
        return userRepository.findByUserName(login).orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

}

