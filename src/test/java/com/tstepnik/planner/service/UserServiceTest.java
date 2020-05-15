package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.initMocks(this.service);
        MockitoAnnotations.initMocks(this.userRepository);
    }

    @Test
    public void testGetUsersEmptyRepository() {
        when(service.getUsers()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(service.getUsers(), Collections.emptyList());
    }

    @Test
    public void testGetLoggedUser() {
        User jon = new User("user", "user", "user@gmail.com");
        User mark = new User("usdger", "userdfg", "user@gmail.com");
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "user";
            }
        };
        jon.setId(1L);
        mark.setId(2L);
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(jon));
        User testUser = service.getLoggedUser(principal);
        Assertions.assertEquals(
                jon, testUser);
    }


}
