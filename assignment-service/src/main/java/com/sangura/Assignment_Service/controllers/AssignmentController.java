package com.sangura.Assignment_Service.controllers;

import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.entities.Assignment;
import com.sangura.Assignment_Service.services.AssignmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<AssignmentDto>> getAllAssignments(){
        return ResponseEntity.ok(assignmentService.getAllAssignment());
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentCreateDto assignmentCreateDto){
        return ResponseEntity.ok(assignmentService.createAssignment(assignmentCreateDto));

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<AssignmentDto> updateAssignment(@PathVariable Long id, @RequestBody AssignmentCreateDto assignmentCreateDto){
        return ResponseEntity.ok(assignmentService.updateAssignment(id, assignmentCreateDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> deleteAssignment(@PathVariable Long id){
        return ResponseEntity.ok(assignmentService.deleteAssignment(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<AssignmentDto> findAssignmentById(@PathVariable Long id){
        return ResponseEntity.ok(assignmentService.findAssignmentById(id));
    }
}
