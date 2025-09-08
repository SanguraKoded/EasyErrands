package com.sangura.Assignment_Service.services;

import com.sangura.common.events.DTOs.AssignmentEvent;
import com.sangura.common.events.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssignmentEventProducer {

    private final KafkaTemplate<String, AssignmentEvent> kafkaTemplate;

    public AssignmentEventProducer(KafkaTemplate<String, AssignmentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAssignmentCreated(AssignmentEvent event){
        kafkaTemplate.send(KafkaTopics.Assignment_Created, String.valueOf(event.getAssignmentId()), event);
    }
    public void publishAssignmentUpdated(AssignmentEvent event){
        kafkaTemplate.send(KafkaTopics.Assignment_Updated, String.valueOf(event.getAssignmentId()), event);
    }
    public void publishAssignmentDeleted(AssignmentEvent event){
        kafkaTemplate.send(KafkaTopics.Assignment_Deleted, String.valueOf(event.getAssignmentId()), event);
    }

}
