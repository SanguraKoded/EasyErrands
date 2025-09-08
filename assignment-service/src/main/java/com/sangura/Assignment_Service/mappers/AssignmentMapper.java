package com.sangura.Assignment_Service.mappers;

import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.entities.Assignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    Assignment toEntity(AssignmentCreateDto assignmentCreateDto);

    AssignmentDto toDto(Assignment assignment);
}
