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
@RequestMapping("/api/users")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
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
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task userTask, @PathVariable("id") Long userId) {
        Task task = taskService.addTask(userTask, userId);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{user-id}/tasks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable("id") Long taskId,
                                           @PathVariable("user-id") Long userId) {
        Task checkedTask = taskService.getTask(taskId).get();
        if (checkedTask.getUserId().equals(userId)) {
            Task updatedTask = taskService.updateTask(task, taskId);
            return ResponseEntity.ok(updatedTask);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") @RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
