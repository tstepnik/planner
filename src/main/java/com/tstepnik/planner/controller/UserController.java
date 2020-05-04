package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.User;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getLoggedUser(Principal principal) {
        CustomUserDetails customUserDetails = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ResponseEntity.ok(customUserDetails.getUser());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updatedUser(@RequestBody User user, @PathVariable("id") long id) {
        Optional<User> updateUser = Optional.ofNullable(userService.updateUser(user, id));
        if (updateUser.isEmpty()) {
            return new ResponseEntity<User>(updateUser.get(), HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(updateUser.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

