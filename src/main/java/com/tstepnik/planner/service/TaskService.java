package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.*;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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
        Task task = taskRepository.getById(taskId);
        if (!task.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("This task doesn't belongs to User");
        } else {
            return task;
        }
    }

    public List<Task> getUserTasks() {
        User user = authService.getLoggedUser();
        return taskRepository.findAllByUserId(user.getId());
    }

    public Task addTask(Task task) {
        User user = authService.getLoggedUser();
        if (task.getImportance() == null) {
            task.setImportance(DEFAULT_IMPORTANCE);
        }
        task.setUserId(user.getId());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long taskId) {
        User user = authService.getLoggedUser();
        Task checkedTask = taskRepository.getById(taskId);
        if (!checkedTask.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("This task doesn't belongs to User");
        } else {
            checkedTask.setImportance(task.getImportance());
            checkedTask.setDescription(task.getDescription());
            checkedTask.setDone(task.isDone());
            return taskRepository.save(checkedTask);
        }
    }

    public void deleteTask(Long taskId) {
        User user = authService.getLoggedUser();
        Task task = taskRepository.getById(taskId);
        if (!task.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("This task doesn't belongs to User");
        } else {
            taskRepository.deleteById(taskId);
        }
    }
}
