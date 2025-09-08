package com.sangura.Assignment_Service.services;

import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.entities.Assignment;

import java.util.List;

public interface AssignmentService {

    AssignmentDto createAssignment(AssignmentCreateDto assignmentCreateDto);
    List<AssignmentDto> getAllAssignment();

    String deleteAssignment(Long id);

    AssignmentDto findAssignmentById(Long id);

    AssignmentDto updateAssignment(Long id, AssignmentCreateDto assignmentCreateDto);

}
