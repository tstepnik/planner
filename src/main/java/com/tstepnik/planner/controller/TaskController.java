package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Task>> tasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Task>> userTasks(Principal principal) {
        List<Task> tasks = taskService.userTasks(principal);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/addtask")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@RequestBody Task userTask, Principal principal) {
        Task task = taskService.addTask(userTask, principal);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@RequestBody Task task,
                                           @RequestParam Long id, Principal principal) {
        Task updatedTask = taskService.updateTask(task, id, principal);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@RequestParam Long id, Principal principal) {
        taskService.deleteTask(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
