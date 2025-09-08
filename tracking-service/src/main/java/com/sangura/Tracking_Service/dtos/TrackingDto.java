package com.sangura.Tracking_Service.dtos;

import java.time.LocalDateTime;

public class TrackingDto {

    private long assignmentId;

    private Boolean isComplete;

    private Boolean isDelayed;

    private long delayedTime;

    private LocalDateTime timeCreated;

    private LocalDateTime deadLine;

    public TrackingDto(long assignmentId, Boolean isComplete, Boolean isDelayed, long delayedTime, LocalDateTime timeCreated, LocalDateTime deadLine) {
        this.assignmentId = assignmentId;
        this.isComplete = isComplete;
        this.isDelayed = isDelayed;
        this.delayedTime = delayedTime;
        this.timeCreated = timeCreated;
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return "TrackingDto{" +
                "assignmentId=" + assignmentId +
                ", isComplete=" + isComplete +
                ", isDelayed=" + isDelayed +
                ", delayedTime=" + delayedTime +
                ", timeCreated=" + timeCreated +
                ", deadLine=" + deadLine +
                '}';
    }

    public TrackingDto() {
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Boolean getDelayed() {
        return isDelayed;
    }

    public void setDelayed(Boolean delayed) {
        isDelayed = delayed;
    }

    public long getDelayedTime() {
        return delayedTime;
    }

    public void setDelayedTime(long delayedTime) {
        this.delayedTime = delayedTime;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }
}
