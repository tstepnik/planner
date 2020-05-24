package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Statistics;
import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.repository.StatisticsRepository;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StatisticsService {

    private final TaskRepository taskRepository;
    private final StatisticsRepository statisticsRepository;
    private final AuthService authService;

    public StatisticsService(TaskRepository taskRepository, StatisticsRepository statisticsRepository,
                             AuthService authService) {
        this.taskRepository = taskRepository;
        this.statisticsRepository = statisticsRepository;
        this.authService = authService;
    }

    public Statistics createSaveAndReturnStatistics() {
        User loggedUser = authService.getLoggedUser();
        Statistics statistics = statisticsRepository.findFirstByUserIdOrderByIdDesc(loggedUser.getId());
        if (statistics == null || statistics.getCreationDate().toLocalDate().isBefore(LocalDate.now())) {
            LocalDateTime now = LocalDateTime.now();
            DecimalFormat df = new DecimalFormat("#.#");
            Integer userArchivedTasks = countArchivedTasks();
            Integer userFinishedTasks = countFinishedTasks();
            Double userProductivity = ((double) userFinishedTasks / userArchivedTasks) * 100;
            String format = df.format(userProductivity).replaceAll(",", ".");
            Double productivity = Double.valueOf(format);
            return statisticsRepository.save(new Statistics(loggedUser.getId(), productivity, userArchivedTasks, userFinishedTasks, now));

        }
        return statistics;
    }

    public Integer countArchivedTasks() {
        User loggedUser = authService.getLoggedUser();
        LocalDateTime now = LocalDateTime.now();
        Long numberOfTasks = taskRepository.countAllUserArchivedTasks(loggedUser.getId(), now);
        return numberOfTasks.intValue();
    }

    public Integer countFinishedTasks() {
        User loggedUser = authService.getLoggedUser();
        LocalDateTime now = LocalDateTime.now();
        Long numberOfTasks = taskRepository.countAllUserFinishAndArchivedTasks(loggedUser.getId(), now);
        return numberOfTasks.intValue();
    }
}
