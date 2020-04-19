package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Task.Importance;
import com.tstepnik.planner.domain.Task.Task;
import com.tstepnik.planner.service.TaskService;
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
    public ResponseEntity<List<Task>> getAllUserTasks(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    @GetMapping("/{importance}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable("importance") Importance importance) {
        return ResponseEntity.ok(taskService.getTaskByImportance(importance));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


    @GetMapping("/mytasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getAllLoggedUserTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getAllLoggedUserTasks(principal));
    }

    @GetMapping("/mytasks/{importance}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getMyParticularTasks(Principal principal,
                                                         @PathVariable("importance") Importance importance) {
        return ResponseEntity.ok(taskService.getLoggedUserParticularTasks(principal, importance));
    }

    @PostMapping("/addtask")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> addTask(@RequestBody Task task, Principal principal) {
        return ResponseEntity.ok(taskService.addTask(task, principal));
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



    @GetMapping("/sumOfMyTasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getNumberOfMyArchivedTasks(Principal principal){
        Optional<Long> sumOfArchivedTasks = taskService.getSumOfArchivedTasksByLogin(principal);
        if (sumOfArchivedTasks.isPresent()){
            return ResponseEntity.ok(sumOfArchivedTasks.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
