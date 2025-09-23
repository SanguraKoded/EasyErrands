package com.sangura.Assignment_Service.controllers;

import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.entities.Assignment;
import com.sangura.Assignment_Service.services.AssignmentService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<AssignmentDto>> getAllAssignments(){
        return ResponseEntity.ok(assignmentService.getAllAssignment());
    }

    @PostMapping(value = "/admin/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentCreateDto assignmentCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.createAssignment(assignmentCreateDto));

    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<AssignmentDto> updateAssignment(@PathVariable ("id") Long id, @RequestBody AssignmentCreateDto assignmentCreateDto){
        return ResponseEntity.ok(assignmentService.updateAssignment(id, assignmentCreateDto));
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> deleteAssignment(@PathVariable ("id") Long id){
        return ResponseEntity.ok(assignmentService.deleteAssignment(id));
    }

    @GetMapping("/admin/get/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<AssignmentDto> findAssignmentById(@PathVariable ("id") Long id){
        return ResponseEntity.ok(assignmentService.findAssignmentById(id));
    }
}
