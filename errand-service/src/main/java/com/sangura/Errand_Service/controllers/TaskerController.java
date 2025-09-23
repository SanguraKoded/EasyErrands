package com.sangura.Errand_Service.controllers;

import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.entities.Tasker;
import com.sangura.Errand_Service.services.TaskerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasker")
public class TaskerController {

    private final TaskerService taskerService;

    public TaskerController(TaskerService taskerService) {
        this.taskerService = taskerService;
    }

    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskerSavedDto> createTasker(@RequestBody TaskerDto taskerDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskerService.createTasker(taskerDto));
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTasker(@PathVariable("id") Long id){
        return ResponseEntity.ok(taskerService.deleteTasker(id));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskerSavedDto>> getAll(){
        return ResponseEntity.ok(taskerService.getAllTaskers());
    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskerSavedDto> updateTasker(@PathVariable("id") Long id, @RequestBody TaskerDto taskerDto){
        return ResponseEntity.ok(taskerService.updateTasker(id, taskerDto));
    }
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskerSavedDto> findTaskerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(taskerService.getTaskerById(id));
    }
}
