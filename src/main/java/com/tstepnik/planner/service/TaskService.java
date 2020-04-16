package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Task.Importance;
import com.tstepnik.planner.domain.Task.Task;
import com.tstepnik.planner.domain.User.User;
import com.tstepnik.planner.repository.TaskRepository;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
public class TaskService {

    private final Importance DEFAULT_IMPORTANCE = Importance.LITTLE_IMPORTANT;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllLoggedUserTasks(Principal principal) {
        User loggedUser = userRepository.findByLogin(principal.getName()).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        return taskRepository.findAllByUser(loggedUser);
    }

    public List<Task> getLoggedUserTasks(Principal principal,Importance importance) {
        User loggedUser = userRepository.findByLogin(principal.getName()).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        return taskRepository.findAllByUserIdaAndImportance(loggedUser.getId(),importance);
    }

    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findALLByUserId(userId);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    public Task addTask(Task task) {
        task.setImportance(DEFAULT_IMPORTANCE);
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id) {
        Task updatedTask = taskRepository.findById(id).get();
        updatedTask.setImportance(task.getImportance());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setArchived(task.isArchived());
        updatedTask.setDone(task.isDone());
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> getTaskByImportance(Importance importance){
    return taskRepository.findAllByImportance(importance);
    }

}
