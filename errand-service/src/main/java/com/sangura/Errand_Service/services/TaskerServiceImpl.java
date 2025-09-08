package com.sangura.Errand_Service.services;

import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.entities.Tasker;
import com.sangura.Errand_Service.mappers.TaskerMapper;
import com.sangura.Errand_Service.repos.TaskerRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RefreshScope
public class TaskerServiceImpl implements TaskerService{

    private static final Logger log = LoggerFactory.getLogger(TaskerServiceImpl.class);


    private final TaskerMapper taskerMapper;

    private final TaskerRepo taskerRepo;

    public TaskerServiceImpl(TaskerMapper taskerMapper, TaskerRepo taskerRepo) {
        this.taskerMapper = taskerMapper;
        this.taskerRepo = taskerRepo;
    }

    @Override
    @Transactional
    public TaskerSavedDto createTasker(TaskerDto taskerDto) {

        Tasker tasker = taskerMapper.toEntity(taskerDto);
        log.info("Mapped entity: {}", tasker);
        tasker.setErrandsCompleted(0);
        tasker.setRatings(0);
        tasker.setActiveErrands(null);
        Tasker savedTasker = taskerRepo.save(tasker);
        log.info("Saved tasker: {}", savedTasker);
        TaskerSavedDto savedDto = taskerMapper.toDto(savedTasker);
        return savedDto;
    }

    @Override
    @Transactional
    @CacheEvict(value="tasker", key="#id")
    public String deleteTasker(Long id) {
        taskerRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        taskerRepo.deleteById(id);
        return "Successfully Deleted Tasker";
    }

    @Override
    @Transactional
    @CachePut(value="tasker", key="#id")
    public TaskerSavedDto updateTasker(Long id, TaskerDto taskerDto) {
        Tasker tasker = taskerRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        Tasker updatedTasker = taskerMapper.toEntity(taskerDto);
        updatedTasker.setActiveErrands(tasker.getActiveErrands());
        updatedTasker.setRatings(tasker.getRatings());
        updatedTasker.setErrandsCompleted(tasker.getErrandsCompleted());
        taskerRepo.save(updatedTasker);
        return taskerMapper.toDto(updatedTasker);
    }

    @Override
    @Transactional
    @Cacheable(value="taskers", key="#id")
    public List<TaskerSavedDto> getAllTaskers() {

        List<Tasker> allTaskers =  taskerRepo.findAll();
        List<TaskerSavedDto> allTaskerDtos = new ArrayList<>();
        for(Tasker tasker : allTaskers){
            allTaskerDtos.add(taskerMapper.toDto(tasker));
        }
        return allTaskerDtos;
    }

    @Override
    @Transactional
    @Cacheable(value="tasker", key="#id")
    public TaskerSavedDto getTaskerById(Long id) {
        Tasker foundTasker =  taskerRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Valid Tasker ID"));
        return taskerMapper.toDto(foundTasker);
    }
}
