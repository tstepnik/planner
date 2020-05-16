package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //TODO consider situations when null could be returned or which errors could be throwed.
    public User getLoggedUser() {
        return authService.getLoggedUser();
    }

    public User updateUser(User user, long id) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        currentUser.setPassword(user.getPassword());
        currentUser.setEmail(user.getEmail());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setLogin(user.getLogin());
        return userRepository.save(currentUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
