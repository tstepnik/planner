package com.tstepnik.planner.controller;
import com.tstepnik.planner.domain.User.User;
import com.tstepnik.planner.domain.User.UserDTO;
import com.tstepnik.planner.domain.User.UserMapper;
import com.tstepnik.planner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getUsers();

        return ResponseEntity.ok(mapper.userToUserDTO(users));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> getLoggedUser(Principal principal) {
        Optional<User> user = Optional.ofNullable(userService.getLoggedUser(principal));
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(mapper.userToUserDTO(user.get()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        User user = userService.getUser(id);
        return ResponseEntity.ok(mapper.userToUserDTO(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> updatedUser(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> updateUser = Optional.ofNullable(userService.updateUser(user, id));
        if (updateUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(mapper.userToUserDTO(updateUser.get()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}