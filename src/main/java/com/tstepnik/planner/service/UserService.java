package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.domain.UserRole;
import com.tstepnik.planner.repository.UserRepository;
import com.tstepnik.planner.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final static String DEFAULT_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addWithDefaultRole(User user) {
        Optional<UserRole> defaultRole = roleRepository.findById(1L);
        user.getRoles().add(defaultRole.get());
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }
}
