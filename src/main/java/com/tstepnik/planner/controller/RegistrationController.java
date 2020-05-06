package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserRegisterDTO;
import com.tstepnik.planner.domain.user.RegisterUserMapper;
import com.tstepnik.planner.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegisterUserMapper mapper;

    @Autowired
    public RegistrationController(RegistrationService registrationService, RegisterUserMapper mapper) {
        this.registrationService = registrationService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> registerUser(@RequestBody UserRegisterDTO user) {
        Optional<User> registerUser = Optional.ofNullable(registrationService.register(mapper.toEntity(user)));
        if (registerUser.isEmpty()) {
            //TODO remove it once you add exception handler.
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
