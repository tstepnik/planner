package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Statistics;
import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.repository.StatisticsRepository;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class StatisticsService {

    TaskRepository taskRepository;
    StatisticsRepository statisticsRepository;
    TaskService taskService;
    AuthService authService;

    public StatisticsService(TaskRepository taskRepository, StatisticsRepository statisticsRepository,
                             TaskService taskService, AuthService authService) {
        this.taskRepository = taskRepository;
        this.statisticsRepository = statisticsRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    public Statistics getActualStatistics(){
       return statisticsRepository.findFirstByOrderByIdDesc();
    }

    public void createAndGetStatistics() {
        User loggedUser = authService.getLoggedUser();
        Integer userArchivedTasks = countArchivedTasks();
        Integer userFinishedTasks = countFinishTasks();
        Double userProductivity = (double) userFinishedTasks / userArchivedTasks;
        statisticsRepository.save(new Statistics(loggedUser.getId(), userProductivity, userArchivedTasks, userFinishedTasks));
    }

    public Integer countArchivedTasks() {
        User loggedUser = authService.getLoggedUser();
        ZonedDateTime now = ZonedDateTime.now();
        Long numberOfTasks = taskRepository.countAllUserArchivedTasks(loggedUser.getId());
        return numberOfTasks.intValue();
    }

    public Integer countFinishTasks() {
        User loggedUser = authService.getLoggedUser();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        Long numberOfTasks = taskRepository.countAllByUserIdAndDoneIsTrue(loggedUser.getId());
        return numberOfTasks.intValue();
    }
}
