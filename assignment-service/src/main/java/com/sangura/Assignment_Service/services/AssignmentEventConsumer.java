package com.sangura.Assignment_Service.services;

import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.common.events.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AssignmentEventConsumer {

    private final AssignmentService assignmentService;

    public AssignmentEventConsumer(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @KafkaListener(topics = KafkaTopics.Errand_Created, groupId = "assignment-service")
    public void handleErrandCreated(AssignmentCreateDto assignmentCreateDto) {
        assignmentService.createAssignment(assignmentCreateDto);
    }


}
