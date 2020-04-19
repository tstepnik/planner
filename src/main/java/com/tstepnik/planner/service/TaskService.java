package com.tstepnik.planner.service;

import com.tstepnik.planner.domain.Task.Importance;
import com.tstepnik.planner.domain.Task.Task;
import com.tstepnik.planner.domain.User.User;
import com.tstepnik.planner.repository.TaskRepository;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        archiveTasks();
        return taskRepository.findAll();
    }

    public List<Task> getAllLoggedUserTasks(Principal principal) {
        User loggedUser = userRepository.findByLogin(principal.getName()).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        archiveTasks();
        return taskRepository.findAllByUser(loggedUser);
    }

    public List<Task> getLoggedUserParticularTasks(Principal principal, Importance importance) {
        User loggedUser = userRepository.findByLogin(principal.getName()).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        archiveTasks();
        return taskRepository.findAllByUserIdaAndImportance(loggedUser.getId(), importance);
    }

    public List<Task> getUserTasks(Long userId) {
        archiveTasks();
        return taskRepository.findALLByUserId(userId);
    }

    public Task getTaskById(Long taskId) {
        archiveTasks();
        Optional<Task> task = taskRepository.findById(taskId);
            return task.get();
    }

    public Task addTask(Task task, Principal principal) {
        task.setImportance(DEFAULT_IMPORTANCE);
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(endOfTheDay());
        task.setUser(userRepository.findByLogin(principal.getName()).get());
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


    public List<Task> getTaskByImportance(Importance importance) {
        return taskRepository.findAllByImportance(importance);
    }


    public LocalDateTime endOfTheDay() {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
    }

    public void archiveTasks() {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            if (LocalDateTime.now().isAfter(task.getExpirationDate())) {
                task.setArchived(true);
                taskRepository.save(task);
            }
        });
    }

    public Optional<Long> getSumOfArchivedTasksById(Long userId){
        Optional<Long> sumOfTasks = taskRepository.countAllArchivedTaskByUserId(userId);
        return sumOfTasks;
    }

    public Optional<Long> getSumOfArchivedTasksByLogin(Principal principal){
        Optional<Long> sumOfTasks = taskRepository.countAllArchivedTaskByLogin(principal.getName());
        return sumOfTasks;
    }
}
