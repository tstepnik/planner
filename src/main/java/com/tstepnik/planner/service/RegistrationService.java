package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.domain.UserRole;
import com.tstepnik.planner.exceptions.EmailAlreadyUsedException;
import com.tstepnik.planner.exceptions.UserAlreadyExistException;
import com.tstepnik.planner.repository.UserRepository;
import com.tstepnik.planner.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private final static String DEFAULT_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, UserRoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (emailExist(user.getEmail())) {
            throw new EmailAlreadyUsedException("There is account with that email adress.");
        } else if (loginExist(user.getLogin())) {
            throw new UserAlreadyExistException("There is account with that user name.");
        }
        Optional<UserRole> defaultRole = roleRepository.findById(1L);
        user.getRoles().add(defaultRole.get());
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean loginExist(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean isExist(User user) {
        return userRepository.existsById(user.getId());
    }
}
