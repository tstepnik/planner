package com.tstepnik.planner;

import com.tstepnik.planner.domain.Role;
import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.domain.UserRole;
import com.tstepnik.planner.repository.UserRepository;
import com.tstepnik.planner.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class PlannerApplication {

    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlannerApplication(UserRoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }



    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
    }

    @PostConstruct
    private void init(){
        UserRole userRole = new UserRole(Role.ROLE_USER);
        UserRole adminRole = new UserRole(Role.ROLE_ADMIN);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        User user = new User("user", "user");
        user.getRoles().add(userRole);
        User admin = new User ("admin", "admin");
        admin.getRoles().add(adminRole);
        admin.getRoles().add(userRole);
        userRepository.saveAll(Arrays.asList(user,admin));

    }

}
