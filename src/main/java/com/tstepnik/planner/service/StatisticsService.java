package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Statistics;
import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.StatisticsRepository;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class StatisticsService {

    private final TaskRepository taskRepository;
    private final StatisticsRepository statisticsRepository;
    private final TaskService taskService;
    private final AuthService authService;

    private int countUserAskedForStatistics = 0;
    private ZonedDateTime firstTimeUserGetStatistics = ZonedDateTime.now(ZoneId.of("UTC"));
    private Statistics statistics = null;

    public StatisticsService(TaskRepository taskRepository, StatisticsRepository statisticsRepository,
                             TaskService taskService, AuthService authService) {
        this.taskRepository = taskRepository;
        this.statisticsRepository = statisticsRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    public Statistics createSaveAndReturnStatistics() {

        if (countUserAskedForStatistics < 1) {
            firstTimeUserGetStatistics = ZonedDateTime.now();
            countUserAskedForStatistics++;

            DecimalFormat df = new DecimalFormat("##.#");
            User loggedUser = authService.getLoggedUser();
            Integer userArchivedTasks = countArchivedTasks();
            Integer userFinishedTasks = countFinishTasks();
            Double userProductivity = ((double) userFinishedTasks / userArchivedTasks) * 100;
            String format = df.format(userProductivity).replaceAll(",", ".");
            Double productivity = Double.valueOf(format);
            statistics = new Statistics(loggedUser.getId(), productivity, userArchivedTasks, userFinishedTasks);

            statisticsRepository.save(statistics);
        }
        if (firstTimeUserGetStatistics.getDayOfMonth() != ZonedDateTime.now().getDayOfMonth()) {
            countUserAskedForStatistics = 0;
        }
        return statistics;
    }

    public Integer countArchivedTasks() {
        User loggedUser = authService.getLoggedUser();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        Long numberOfTasks = taskRepository.countAllUserArchivedTasks(loggedUser.getId(), now);
        return numberOfTasks.intValue();
    }

    public Integer countFinishTasks() {
        User loggedUser = authService.getLoggedUser();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        Long numberOfTasks = taskRepository.countAllUserFinishAndArchivedTasks(loggedUser.getId(), now);
        return numberOfTasks.intValue();
    }
}
