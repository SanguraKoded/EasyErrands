package com.sangura.Assignment_Service.services;

import com.sangura.Assignment_Service.client.ErrandServiceClient;
import com.sangura.Assignment_Service.client.TaskerServiceClient;
import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.entities.Assignment;
import com.sangura.Assignment_Service.mappers.AssignmentMapper;
import com.sangura.Assignment_Service.repos.AssignmentRepo;
import com.sangura.common.events.DTOs.AssignmentEvent;
import com.sangura.common.events.KafkaTopics;
import com.sangura.common.events.enums.AssignmentEventType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class AssignmentServiceImpl implements AssignmentService{

    private final AssignmentRepo assignmentRepo;
    private final AssignmentMapper  assignmentMapper;

    private final AssignmentEventProducer assignmentEventProducer;

    private final ErrandServiceClient errandClient;

    private final TaskerServiceClient taskerClient;

    public AssignmentServiceImpl(AssignmentRepo assignmentRepo, AssignmentMapper assignmentMapper, AssignmentEventProducer assignmentEventProducer, ErrandServiceClient errandClient, TaskerServiceClient taskerClient) {
        this.assignmentRepo = assignmentRepo;
        this.assignmentMapper = assignmentMapper;
        this.assignmentEventProducer = assignmentEventProducer;
        this.errandClient = errandClient;
        this.taskerClient = taskerClient;
    }

    @Override
    @Transactional
    public AssignmentDto createAssignment(AssignmentCreateDto assignmentCreateDto) {
        Assignment assignment = assignmentMapper.toEntity(assignmentCreateDto);
        try{
            errandClient.findErrandById(assignmentCreateDto.getErrandId());
        }catch (Exception e){
            throw new RuntimeException("Errand not found: "+ assignmentCreateDto.getErrandId());
        }
        try{
            taskerClient.findTaskerById(assignmentCreateDto.getTaskerId());
        }catch (Exception e){
            throw new RuntimeException("Tasker not found: "+assignmentCreateDto.getTaskerId());
        }
        assignment.setCreatedAt(LocalDateTime.now());
        Assignment saved = assignmentRepo.save(assignment);
        AssignmentEvent assignmentEvent = new AssignmentEvent();
        assignmentEvent.setAssignmentId(saved.getId());
        assignmentEvent.setEventType(AssignmentEventType.CREATED);
        assignmentEvent.setErrandId(saved.getErrandId());
        assignmentEvent.setTaskerId(saved.getTaskerId());
        assignmentEvent.setCreatedAt(LocalDateTime.now());
        assignmentEventProducer.publishAssignmentCreated(assignmentEvent);
        AssignmentDto dto = assignmentMapper.toDto(saved);
        System.out.println("MAPPED DTO: " + dto);
        return dto;
    }

    @Override
    @Transactional
    @Cacheable(value="assignments", key="#id")
    public List<AssignmentDto> getAllAssignment() {

        List<Assignment> allAssignmentsEntities= assignmentRepo.findAll();
        List<AssignmentDto> allAssignmentDtos = new ArrayList<>();
        for(Assignment assignmentEntity : allAssignmentsEntities){
            allAssignmentDtos.add(assignmentMapper.toDto(assignmentEntity));
        }
        return allAssignmentDtos;
    }

    @Override
    @Transactional
    @CacheEvict(value="assignments", key="#id")
    public String deleteAssignment(Long id) {
        Assignment assignment = assignmentRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Valid ID"));
        assignmentRepo.delete(assignment);
        AssignmentEvent assignmentEvent = new AssignmentEvent();
        assignmentEvent.setAssignmentId(assignment.getId());
        assignmentEvent.setEventType(AssignmentEventType.CANCELLED);
        assignmentEvent.setErrandId(assignment.getErrandId());
        assignmentEvent.setTaskerId(assignment.getTaskerId());
        assignmentEvent.setCreatedAt(LocalDateTime.now());
        assignmentEventProducer.publishAssignmentDeleted(assignmentEvent);
        return "Successfully Deleted";
    }

    @Override
    @Transactional
    @Cacheable(value="assignment", key="#id")
    public AssignmentDto findAssignmentById(Long id) {

        Assignment foundAssignment =  assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Correct ID"));
        return assignmentMapper.toDto(foundAssignment);
    }

    @Override
    @Transactional
    @CachePut(value="assignments", key="#id")
    public AssignmentDto updateAssignment(Long id, AssignmentCreateDto assignmentCreateDto) {
        assignmentRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Valid ID"));
        try{
            errandClient.findErrandById(assignmentCreateDto.getErrandId());
        }catch (Exception e){
            throw new RuntimeException("Errand not found: "+ assignmentCreateDto.getErrandId());
        }
        try{
            taskerClient.findTaskerById(assignmentCreateDto.getTaskerId());
        }catch (Exception e){
            throw new RuntimeException("Tasker not found: "+assignmentCreateDto.getTaskerId());
        }
        Assignment savedAssignment =  assignmentRepo.save(assignmentMapper.toEntity(assignmentCreateDto));
        AssignmentEvent assignmentEvent = new AssignmentEvent();
        assignmentEvent.setAssignmentId(savedAssignment.getId());
        assignmentEvent.setEventType(AssignmentEventType.UPDATED);
        assignmentEvent.setErrandId(savedAssignment.getErrandId());
        assignmentEvent.setTaskerId(savedAssignment.getTaskerId());
        assignmentEvent.setCreatedAt(LocalDateTime.now());
        assignmentEventProducer.publishAssignmentUpdated(assignmentEvent);
        return assignmentMapper.toDto(savedAssignment);
    }


}
