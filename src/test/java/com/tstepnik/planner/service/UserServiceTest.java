package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private static User jon;
    private static User mark;

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.initMocks(this.service);
        MockitoAnnotations.initMocks(this.userRepository);
        MockitoAnnotations.initMocks(this.authService);
    }

    @BeforeAll
    public static void init() {
        jon = new User("user", "user", "user@gmail.com");
        mark = new User("superUser", "superPassword", "user@gmail.com");
    }

    @Test
    public void testGetUsersEmptyRepository() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(service.getUsers(), Collections.emptyList());
    }

    @Test
    public void getLoggedUser() {
        jon.setId(1L);
        mark.setId(2L);
        when(authService.getLoggedUser()).thenReturn(mark);
        Assertions.assertEquals(service.getLoggedUser(), mark);
    }

    @Test
    public void updateWhenNotFound() {
        mark.setFirstName("John");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateUser(mark, 1L);
        });
    }

    @Test
    public void updateWhenUserIsPresent() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mark));
        mark.setFirstName("John");
        when(userRepository.save(mark)).thenReturn(mark);
        Assertions.assertEquals(mark, service.updateUser(mark, 1L));
    }
}
