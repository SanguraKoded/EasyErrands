package com.sangura.common.events.DTOs;

import com.sangura.common.events.enums.TrackingEventType;

import java.time.LocalDateTime;

public class TrackingEvent {

    private TrackingEventType eventType;

    private String trackingId;

    private Long assignmentId;

    private String payload;

    private LocalDateTime createdAt;

    public TrackingEvent(TrackingEventType eventType, String trackingId, Long assignmentId, String payload, LocalDateTime createdAt) {
        this.eventType = eventType;
        this.trackingId = trackingId;
        this.assignmentId = assignmentId;
        this.payload = payload;
        this.createdAt = createdAt;
    }

    public TrackingEventType getEventType() {
        return eventType;
    }

    public void setEventType(TrackingEventType eventType) {
        this.eventType = eventType;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TrackingEvent() {
    }
}
