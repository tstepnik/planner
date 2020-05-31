package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserDTO;
import com.tstepnik.planner.domain.mappers.UserMapper;
import com.tstepnik.planner.service.AuthService;
import com.tstepnik.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper mapper;

    public UserController(UserService userService, AuthService authService, UserMapper mapper) {
        this.userService = userService;
        this.authService = authService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(mapper.toDto(users));

    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> getLoggedUser() {
        User user = authService.getLoggedUser();
        return ResponseEntity.ok(mapper.toDto(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user, @PathVariable("id") long id) {
        Optional<User> updatedUser = Optional.ofNullable(userService.updateUser(mapper.toEntity(user), id));//TODO Remove OfNullable. Better spring throwing exception.
        if (updatedUser.isEmpty()) {
            return new ResponseEntity<UserDTO>(mapper.toDto(updatedUser.get()), HttpStatus.CONFLICT);//TODO remove when global exception handler is added.
        }
        return ResponseEntity.ok(mapper.toDto(updatedUser.get()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

