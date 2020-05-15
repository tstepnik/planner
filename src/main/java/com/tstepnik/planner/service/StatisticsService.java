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

    public StatisticsService(TaskRepository taskRepository, StatisticsRepository statisticsRepository,
                             TaskService taskService, AuthService authService) {
        this.taskRepository = taskRepository;
        this.statisticsRepository = statisticsRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    public Statistics createSaveAndReturnStatistics() {
        User loggedUser = authService.getLoggedUser();
        Statistics statistics = statisticsRepository.findFirstByUserIdOrderByIdDesc(loggedUser.getId());
        int x = 0;
        if (statistics == null || todayWasNotSavedStatistics(statistics.getCreationDate())) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
            DecimalFormat df = new DecimalFormat("##.#");
            Integer userArchivedTasks = countArchivedTasks();
            Integer userFinishedTasks = countFinishTasks();
            Double userProductivity = ((double) userFinishedTasks / userArchivedTasks) * 100;
            String format = df.format(userProductivity).replaceAll(",", ".");
            Double productivity = Double.valueOf(format);
            return statisticsRepository.save(new Statistics(loggedUser.getId(), productivity, userArchivedTasks, userFinishedTasks, now));

        }
        return statistics;
    }

    private boolean todayWasNotSavedStatistics(ZonedDateTime creationDate) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        return creationDate.getDayOfYear() != now.getDayOfYear() &&
                creationDate.getDayOfYear() <= now.getDayOfYear();
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
