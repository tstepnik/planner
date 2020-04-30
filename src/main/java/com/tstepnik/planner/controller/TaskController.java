package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.service.TaskService;
import com.tstepnik.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mine")
    public ResponseEntity<List<Task>> getUserTasks() {
        List<Task> tasks = taskService.getUserTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task) {
        Task userTask = taskService.addTask(task);
        return new ResponseEntity<>(userTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable("id") Long taskId) {
        Task updatedTask = taskService.updateTask(task, taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") @RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

