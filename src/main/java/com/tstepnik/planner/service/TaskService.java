package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.task.Importance;
import com.tstepnik.planner.domain.task.Task;
import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.exceptions.TaskExpiredException;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        task.setCreationDate(now);
        if (task.getImportance() == null) {
            task.setImportance(DEFAULT_IMPORTANCE);
        }
        if (task.getPlannedFor() == null) {
            LocalDateTime defaultPlannedTime = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
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
        } else if (checkedTask.getPlannedFor().isBefore(LocalDateTime.now())) {
            throw new TaskExpiredException("Time for finish task with id " + checkedTask.getId() + " had expired at: " + checkedTask.getPlannedFor());
        } else {
            checkedTask.setImportance(task.getImportance());
            checkedTask.setDescription(task.getDescription());
            checkedTask.setDone(task.getDone());
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
