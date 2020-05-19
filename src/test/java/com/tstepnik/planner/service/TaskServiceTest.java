package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.task.Importance;
import com.tstepnik.planner.domain.task.Task;
import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.TaskRepository;
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

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    private static Task task1;
    private static Task task2;

    @InjectMocks
    private TaskService service;

    @InjectMocks
    private UserService userService;

    @Mock
    private TaskRepository repository;

    @Mock
    private AuthService authService;


    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.initMocks(this.service);
        MockitoAnnotations.initMocks(this.repository);
        MockitoAnnotations.initMocks(this.authService);
        MockitoAnnotations.initMocks(this.userService);
    }

    @BeforeAll
    public static void init() {

        task1 = new Task();
        task2 = new Task();

        task1.setIsDone(true);
        task1.setImportance(Importance.NORMAL);
        task1.setDescription("first Task");
        task1.setUserId(1L);

        task2.setDescription("Second Task");
        task2.setUserId(2L);
    }

    @Test
    public void findAllEmptyRepository() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(service.findAll(),Collections.emptyList());
    }

    @Test
    public void getTaskNotEmpty(){
        User mark = new User("user","user","user@email.com");
        mark.setId(1L);

        when(authService.getLoggedUser()).thenReturn(mark);
        when(repository.getOne(1L)).thenReturn(task1);
        Assertions.assertEquals(service.getTask(1L),task1);
    }

}
