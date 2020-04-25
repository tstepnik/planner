package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Importance;
import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.exceptions.UserNotFoundException;
import com.tstepnik.planner.repository.TaskRepository;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final Importance DEFAULT_IMPORTANCE = Importance.INCIDENTAL;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTask(Long id){
        return taskRepository.findById(id);
    }

    public Task addTask(Task task, Long userId) {
        task.setImportance(DEFAULT_IMPORTANCE);
        task.setUser(userRepository.findById(userId).get());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id) {
        Optional<Task> userTask = taskRepository.findById(id);
        if (userTask.isEmpty()) {
            return taskRepository.save(task);
        }
        Task updatedTask = userTask.get();
        updatedTask.setImportance(task.getImportance());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setDone(task.isDone());
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id, Principal principal) {
        taskRepository.deleteUserTask(id, principal.getName());
    }

    public List<Task> userTasks(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }
}
