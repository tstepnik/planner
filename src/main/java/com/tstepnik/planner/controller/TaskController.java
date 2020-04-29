package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task;
import com.tstepnik.planner.service.TaskService;
import com.tstepnik.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Task>> tasks() {
        return ResponseEntity.ok(taskService.findAll());
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mine")
    public ResponseEntity<List<Task>> userTasks(Principal principal) {
        Long userId = taskService.userId(principal);
        List<Task> tasks = taskService.userTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task userTask,Principal principal) {
        Long userId = taskService.userId(principal);
        if (userId.equals(userTask.getUserId())) {
            Task task = taskService.addTask(userTask, userId);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable("id") Long taskId,Principal principal){
        Task checkedTask = taskService.getTask(taskId).get();
        Long userId = taskService.userId(principal);
        if (checkedTask.getUserId().equals(userId)) {
            Task updatedTask = taskService.updateTask(task, taskId);
            return ResponseEntity.ok(updatedTask);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") @RequestParam Long taskId,Principal principal) {
        Long userId = taskService.userId(principal);
        Optional<Task> task = taskService.getTask(taskId);
        if (task.get().getUserId().equals(userId)){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
