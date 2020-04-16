package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task.Task;
import com.tstepnik.planner.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}/usertasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/mytasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getLoggedUserTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getLoggedUserTasks(principal));
    }

    @PostMapping("/addtask")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable("id") Long id) {
        Task updatedTask = taskService.updateTask(task, id);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
