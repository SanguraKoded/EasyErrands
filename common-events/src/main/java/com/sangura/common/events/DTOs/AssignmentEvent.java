package com.sangura.common.events.DTOs;

import com.sangura.common.events.enums.AssignmentEventType;

import java.time.LocalDateTime;

public class AssignmentEvent {

    private AssignmentEventType eventType;

    private Long assignmentId;

    private Long errandId;

    private Long taskerId;

    private LocalDateTime createdAt;

    public AssignmentEvent() {
    }

    public AssignmentEvent(AssignmentEventType eventType, Long assignmentId, Long errandId, Long taskerId, LocalDateTime createdAt) {
        this.eventType = eventType;
        this.assignmentId = assignmentId;
        this.errandId = errandId;
        this.taskerId = taskerId;
        this.createdAt = createdAt;
    }

    public AssignmentEventType getEventType() {
        return eventType;
    }

    public void setEventType(AssignmentEventType eventType) {
        this.eventType = eventType;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getErrandId() {
        return errandId;
    }

    public void setErrandId(Long errandId) {
        this.errandId = errandId;
    }

    public Long getTaskerId() {
        return taskerId;
    }

    public void setTaskerId(Long taskerId) {
        this.taskerId = taskerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
