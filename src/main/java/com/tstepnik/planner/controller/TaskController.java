package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> tasks() {
        return ResponseEntity.ok(taskService.findAll());
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> userTasks(@PathVariable("id") Long userId) {
        List<Task> tasks = taskService.userTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{id}/tasks/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task userTask, Long userId) {
        Task task = taskService.addTask(userTask, userId);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable("id") Long taskId,
                                           Principal principal) {
        Task checkedTask = taskService.getTask(taskId).get();
        if (checkedTask.getUser().equals(task.getUser())) {
            Task updatedTask = taskService.updateTask(task, taskId);
            return ResponseEntity.ok(updatedTask);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@RequestParam Long taskId, Principal principal) {
        Task checkedTask = taskService.getTask(taskId).get();
        String checkedLogin = checkedTask.getUser().getLogin();
        String userLogin = principal.getName();
        if (checkedLogin.equals(userLogin)) {
            taskService.deleteTask(taskId, principal);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
