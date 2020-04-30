package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Importance;
import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.exceptions.TaskNotFoundException;
import com.tstepnik.planner.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final Importance DEFAULT_IMPORTANCE = Importance.MINOR;

    private final TaskRepository taskRepository;
    private final AuthService authService;
//TODO here add whole logic with if and exception and use loggedUser here!


    public TaskService(TaskRepository taskRepository, AuthService authService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
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
        Optional<Task> checkedTask = findTaskById(taskId);
        if (checkedTask.isEmpty()) {
            return taskRepository.save(task);
        } else if (checkedTask.get().getUserId().equals(user.getId())) {
            Task updatedTask = checkedTask.get();
            updatedTask.setImportance(task.getImportance());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setDone(task.isDone());
            return taskRepository.save(updatedTask);
        }
        throw new AccessDeniedException("This task doesn't belongs to User");
    }

    public void deleteTask(Long taskId) {
        User user = authService.getLoggedUser();
        Optional<Task> task = findTaskById(taskId);
        if (task.isPresent()) {
            if (task.get().getUserId().equals(user.getId())) {
                taskRepository.deleteById(taskId);
            } else {
                try {
                    throw new AccessDeniedException("This task doesn't belongs to User");
                } catch (AccessDeniedException e) {
                    e.getMessage();
                }
            }
        } else {
            throw new TaskNotFoundException("There is not such task.You try delete empty task.");
        }
    }
}
