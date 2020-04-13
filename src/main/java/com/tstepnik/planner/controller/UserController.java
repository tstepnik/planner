package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.exceptions.CustomErrorType;
import com.tstepnik.planner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getLoggedUser(Principal principal) {
        String login = principal.getName();
        return userRepository.findByUserName(login).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") long id) {
        Optional<User> currentUser = userRepository.findById(id);
        if (currentUser.equals(Optional.empty())) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setEmail(user.getEmail());
        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());
        currentUser.get().setUserName(user.getUserName());
        userRepository.save(currentUser.get());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.equals(Optional.empty())){
            logger.error("Unable to delete. User with id{} not found.",id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. User wit hid" + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}

