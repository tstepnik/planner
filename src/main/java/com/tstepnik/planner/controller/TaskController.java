package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.task.Task;
import com.tstepnik.planner.domain.task.TaskDTO;
import com.tstepnik.planner.domain.task.TaskMapper;
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
    private final TaskMapper mapper;

    public TaskController(TaskService taskService, UserService userService, TaskMapper mapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(mapper.toDto(tasks));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mine")
    public ResponseEntity<List<TaskDTO>> getUserTasks() {
        List<Task> tasks = taskService.getUserTasks();
        return ResponseEntity.ok(mapper.toDto(tasks));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("id") Long id) {
        Task task = taskService.getTask(id);
        return ResponseEntity.ok(mapper.toDto(task));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO task) {
         taskService.addTask(mapper.toTask(task));
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO task, @PathVariable("id") Long taskId) {
        Task updatedTask = taskService.updateTask(mapper.toTask(task), taskId);
        return ResponseEntity.ok(mapper.toDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
