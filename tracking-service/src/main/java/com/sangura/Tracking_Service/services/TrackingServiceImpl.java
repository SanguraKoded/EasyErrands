package com.sangura.Tracking_Service.services;

import com.sangura.Tracking_Service.client.AssignmentServiceClient;
import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;
import com.sangura.Tracking_Service.mapper.TrackingMapper;
import com.sangura.Tracking_Service.repos.TrackingRepo;
import com.sangura.common.events.DTOs.TrackingEvent;
import com.sangura.common.events.KafkaTopics;
import com.sangura.common.events.enums.TrackingEventType;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RefreshScope
public class TrackingServiceImpl implements TrackingService{

    private final TrackingMapper trackingMapper;

    private final TrackingEventProducer trackingEventProducer;
    private final TrackingRepo trackingRepo;

    private final AssignmentServiceClient assignmentClient;

    public TrackingServiceImpl(TrackingMapper trackingMapper, TrackingEventProducer trackingEventProducer, TrackingRepo trackingRepo, AssignmentServiceClient assignmentClient) {
        this.trackingMapper = trackingMapper;
        this.trackingEventProducer = trackingEventProducer;
        this.trackingRepo = trackingRepo;
        this.assignmentClient = assignmentClient;
    }

    @Override
    @Transactional
    @KafkaListener(topics = KafkaTopics.Errand_Created, groupId = "tracking-service")
    public TrackingDto createTracking(TrackingCreateDto trackingCreateDto) {
        Tracking newTracking = trackingMapper.toEntity(trackingCreateDto);

        newTracking.setComplete(Boolean.FALSE);
        newTracking.setTimeCreated(LocalDateTime.now());
        newTracking.setDelayedTime(0);
        newTracking.setDelayed(Boolean.FALSE);
        try{
            assignmentClient.findAssignmentById(newTracking.getAssignmentId());
        }
        catch(Exception e){
            throw new RuntimeException("Assignment Id not found");
        }
        Tracking savedTracking = trackingRepo.save(newTracking);

        TrackingEvent trackingEvent = new TrackingEvent();
        trackingEvent.setTrackingId(savedTracking.getId());
        trackingEvent.setCreatedAt(savedTracking.getTimeCreated());
        trackingEvent.setEventType(TrackingEventType.STARTED);
        trackingEvent.setAssignmentId(savedTracking.getAssignmentId());
        trackingEventProducer.publishTrackingCreated(trackingEvent);
        TrackingDto savedDto = trackingMapper.toDto(savedTracking);
        return savedDto;
    }

    @Override
    @Transactional
    @CachePut(value="tracking", key="#id")
    public TrackingDto updateTracking(String id, TrackingCreateDto trackingDto) {
        trackingRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Correct ID"));

        Tracking updatedTracking = trackingRepo.save(trackingMapper.toEntity(trackingDto));
        TrackingEvent trackingEvent = new TrackingEvent();
        trackingEvent.setTrackingId(updatedTracking.getId());
        trackingEvent.setCreatedAt(updatedTracking.getTimeCreated());
        trackingEvent.setEventType(TrackingEventType.UPDATED);
        trackingEvent.setAssignmentId(updatedTracking.getAssignmentId());
        trackingEventProducer.publishTrackingUpdated(trackingEvent);
        return trackingMapper.toDto(updatedTracking);
    }

    @Override
    @Transactional
    public String completeTracking(String id) {
        Tracking tracking = trackingRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Correct ID"));
        tracking.setComplete(Boolean.TRUE);
        trackingRepo.save(tracking);
        Tracking updatedTracking = trackingRepo.save(tracking);
        TrackingEvent trackingEvent = new TrackingEvent();
        trackingEvent.setTrackingId(updatedTracking.getId());
        trackingEvent.setEventType(TrackingEventType.COMPLETED);
        trackingEvent.setAssignmentId(updatedTracking.getAssignmentId());
        trackingEventProducer.publishTrackingCompleted(trackingEvent);
        return "Tracking is successfully completed";
    }

    @Override
    @Cacheable(value="tracking", key="#id")
    @Transactional
    public TrackingDto findTrackingById(String id) {

        Tracking foundTracking = trackingRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        return trackingMapper.toDto(foundTracking);
    }
}
