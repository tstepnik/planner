package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    TaskRepository taskRepository;
    TaskService taskService;
    AuthService authService;

    public StatisticsService(TaskRepository taskRepository, TaskService taskService, AuthService authService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    private Double countProductivity(){
        Integer numberOfTasks = countArchivedTasks();
    }


    private Integer countArchivedTasks(){
        long numberOfTasks = taskRepository.count();

        return (int) numberOfTasks;
    }

    private Integer countFinishTasks(){

    }
}
