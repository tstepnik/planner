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

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task, Principal principal) {
        task.setImportance(DEFAULT_IMPORTANCE);
        task.setUser(userRepository.findByLogin(principal.getName()).get());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id, Principal principal) {
        Optional<Task> userTask = taskRepository.findUserTask(id, principal.getName());
        if (userTask.isEmpty()) {
            throw new UserNotFoundException("There is not such user.");
        }
        Task updatedTask = userTask.get();
        updatedTask.setImportance(task.getImportance());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setDone(task.isDone());
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id, Principal principal) {
        taskRepository.deleteYourTask(id, principal.getName());
    }

    public List<Task> userTasks(Principal principal) {
        return taskRepository.findAllByLogin(principal.getName());
    }
}
