package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //TODO change logic, don;t use database for logged user
    public User getLoggedUser(Principal principal) {
        String login = principal.getName();
        return userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User updateUser(User user, long id) {
        Optional<User> currentUser = userRepository.findById(id);//TODO change logic, example: .orElseTrow...
        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setEmail(user.getEmail());
        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());
        currentUser.get().setLogin(user.getLogin());
        return userRepository.save(currentUser.get());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
