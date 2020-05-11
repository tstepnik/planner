package com.tstepnik.planner;

import com.tstepnik.planner.domain.user.Role;
import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserRole;
import com.tstepnik.planner.repository.UserRepository;
import com.tstepnik.planner.repository.UserRoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class PlannerApplication {

    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PlannerApplication(UserRoleRepository roleRepository, UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
    }

    //TODO move this method to SQL file or sth similar, but don't keep it here.
    @PostConstruct
    private void init() {
        UserRole userRole = new UserRole(Role.ROLE_USER);
        UserRole adminRole = new UserRole(Role.ROLE_ADMIN);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        User user = new User("user", passwordEncoder.encode("user"), "user@gmail.com");
        user.getRoles().add(userRole);
        User admin = new User("admin", passwordEncoder.encode("admin"), "admin@gmail.com");
        admin.getRoles().add(adminRole);
        admin.getRoles().add(userRole);
        userRepository.saveAll(Arrays.asList(user, admin));
    }
}
