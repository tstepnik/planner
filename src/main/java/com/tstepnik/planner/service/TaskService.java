package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.*;
import com.tstepnik.planner.exceptions.ExpiredTaskException;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskService {

    private final Importance DEFAULT_IMPORTANCE = Importance.MINOR;

    private final TaskRepository taskRepository;
    private final AuthService authService;

    public TaskService(TaskRepository taskRepository, AuthService authService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task getTask(Long taskId) {
        User user = authService.getLoggedUser();
        Task task = taskRepository.getOne(taskId);
        if (!task.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("Task with id " + taskId + " doesn't belong to user with id " + user.getId());
        } else {
            return task;
        }
    }

    public List<Task> getUserTasks() {
        User user = authService.getLoggedUser();
        return taskRepository.getAllByUserId(user.getId());
    }

    public Task addTask(Task task) {
        User user = authService.getLoggedUser();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS);
        task.setCreationDate(now);
        if (task.getImportance() == null) {
            task.setImportance(DEFAULT_IMPORTANCE);
        }
        if (task.getPlannedFor() == null) {
            LocalTime endOfTheDay = LocalTime.of(23, 59, 59);
            ZonedDateTime defaultPlannedTime = ZonedDateTime.of(LocalDate.now(ZoneId.of("UTC")), endOfTheDay, ZoneId.of("UTC"));
            task.setPlannedFor(defaultPlannedTime);
        }
        task.setUserId(user.getId());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long taskId) {
        User user = authService.getLoggedUser();
        Task checkedTask = taskRepository.getOne(taskId);
        if (!checkedTask.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("User with login " + user.getId() + " cannot edit task with id " + taskId);
        } else if (checkedTask.getPlannedFor().isBefore(ZonedDateTime.now(ZoneId.of("UTC")))) {
            throw new ExpiredTaskException("Time for finish this task had expired at: " + checkedTask.getPlannedFor() + " You can't do any changes on it now.");
        } else {
            checkedTask.setImportance(task.getImportance());
            checkedTask.setDescription(task.getDescription());
            checkedTask.setDone(task.isDone());
            return taskRepository.save(checkedTask);
        }
    }

    public void deleteTask(Long taskId) {
        User user = authService.getLoggedUser();
        Task task = taskRepository.getOne(taskId);
        if (!task.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("User with login " + user.getId() + " cannot delete task with id " + taskId);
        } else {
            taskRepository.deleteById(taskId);
        }
    }
}
