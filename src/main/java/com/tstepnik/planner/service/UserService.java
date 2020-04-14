package com.tstepnik.planner.service;

import com.tstepnik.planner.controller.UserController;
import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.exceptions.CustomErrorType;
import com.tstepnik.planner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getLoggedUser(Principal principal) {
        String login = principal.getName();
        return userRepository.findByUserName(login).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public ResponseEntity<?> updateUser( User user, long id) {
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
        currentUser.get().setLogin(user.getLogin());
        userRepository.save(currentUser.get());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(Long id){
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
