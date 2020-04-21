package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.task.Importance;
import com.tstepnik.planner.domain.task.Task;
import com.tstepnik.planner.domain.task.TaskDTO;
import com.tstepnik.planner.domain.task.TaskMapper;
import com.tstepnik.planner.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    private final TaskService taskService;
    private final TaskMapper mapper;

    public TaskController(TaskService taskService, TaskMapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> getTasks() {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok(mapper.taskToTaskDTO(tasks));
    }

    @GetMapping("/{id}/usertasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> getAllUserTasks(@PathVariable("id") Long userId) {
        List<Task> tasks = taskService.getUserTasks(userId);
        return ResponseEntity.ok(mapper.taskToTaskDTO(tasks));
    }

    @GetMapping("/{importance}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> geTasksByImportance(@PathVariable("importance") Importance importance) {
        return ResponseEntity.ok(taskService.getTaskByImportance(importance));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


    @GetMapping("/mytasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TaskDTO>> getAllLoggedUserTasks(Principal principal) {
        List<Task> tasks = taskService.getAllLoggedUserTasks(principal);
        return ResponseEntity.ok(mapper.taskToTaskDTO(tasks));
    }

    @GetMapping("/mytasks/{importance}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getMyParticularTasks(Principal principal,
                                                           @PathVariable("importance") Importance importance) {
        return ResponseEntity.ok(taskService.getLoggedUserParticularTasks(principal, importance));
    }

    @PostMapping("/addtask")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> addTask(@RequestBody Task userTask, Principal principal) {
        Task task = taskService.addTask(userTask, principal);
        return ResponseEntity.ok(mapper.taskToTaskDTO(task));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody Task task, @PathVariable("id") Long id) {
        Task updatedTask = taskService.updateTask(task, id);
        return ResponseEntity.ok(mapper.taskToTaskDTO(updatedTask));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/sumOfMyTasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getNumberOfMyArchivedTasks(Principal principal) {
        Optional<Long> sumOfArchivedTasks = taskService.getSumOfArchivedTasksByLogin(principal);
        if (sumOfArchivedTasks.isPresent()) {
            return ResponseEntity.ok(sumOfArchivedTasks.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}