package com.sangura.Errand_Service.services;

import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.entities.Tasker;

import java.util.List;

public interface TaskerService {

    TaskerSavedDto createTasker(TaskerDto taskerDto);

    String deleteTasker(Long id);

    TaskerSavedDto updateTasker(Long id, TaskerDto taskerDto);

    List<TaskerSavedDto> getAllTaskers();

    TaskerSavedDto getTaskerById(Long id);
}
