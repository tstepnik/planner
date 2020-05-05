package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserDto;
import com.tstepnik.planner.domain.user.UserMapper;
import com.tstepnik.planner.security.auth.CustomUserDetails;
import com.tstepnik.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(mapper.toDto(users));

    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDto> getLoggedUser(Principal principal) {
        CustomUserDetails customUserDetails = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User user = customUserDetails.getUser();
        return ResponseEntity.ok(mapper.toDto(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDto> updatedUser(@RequestBody UserDto user, @PathVariable("id") long id) {
        Optional<User> updateUser = Optional.ofNullable(userService.updateUser(mapper.toUser(user), id));
        if (updateUser.isEmpty()) {
            return new ResponseEntity<UserDto>(mapper.toDto(updateUser.get()), HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(mapper.toDto(updateUser.get()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

