package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Importance;
import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.repository.TaskRepository;
import com.tstepnik.planner.security.auth.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final Importance DEFAULT_IMPORTANCE = Importance.INCIDENTAL;

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTask(Long id){
        return taskRepository.findById(id);
    }

    public Task addTask(Task task, Long userId) {
        task.setImportance(DEFAULT_IMPORTANCE);
        task.setUserId(userId);
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

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> userTasks(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public Long userId(Principal principal){
        CustomUserDetails customUserDetails =
                (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return customUserDetails.getUser().getId();
    }
}
