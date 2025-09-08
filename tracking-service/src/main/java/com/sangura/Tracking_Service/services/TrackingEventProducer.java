package com.sangura.Tracking_Service.services;

import com.sangura.common.events.DTOs.TrackingEvent;
import com.sangura.common.events.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingEventProducer {

    private final KafkaTemplate<String, TrackingEvent> kafkaTemplate;

    public TrackingEventProducer(KafkaTemplate<String, TrackingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTrackingCreated(TrackingEvent event){
        kafkaTemplate.send(KafkaTopics.Tracking_Created,String.valueOf(event.getTrackingId()),event);
    }

    public void publishTrackingUpdated(TrackingEvent event){
        kafkaTemplate.send(KafkaTopics.Tracking_Updated,String.valueOf(event.getTrackingId()),event);
    }

    public void publishTrackingCompleted(TrackingEvent event){
        kafkaTemplate.send(KafkaTopics.Tracking_Completed,String.valueOf(event.getTrackingId()),event);
    }
}
