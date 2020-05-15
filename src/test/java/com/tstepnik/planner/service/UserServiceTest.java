package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserService service;

    User jon;
    User mark;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        jon = new User("user", "user", "user@gmail.com");
         mark = new User("usdger", "userdfg", "user@gmail.comm");
    }

    @Test
    public void testGetUsersEmptyRepository() {
        List<User> users = new ArrayList<>();
        Mockito.when(service.getUsers()).thenReturn(List.of(jon,mark));
        Assertions.assertEquals(service.getUsers(),users);

    }


}
