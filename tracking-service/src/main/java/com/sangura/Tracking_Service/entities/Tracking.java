package com.sangura.Tracking_Service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "tracking")
public class Tracking {

    @Id
    private String id;

    private long assignmentId;

    private Boolean isComplete;

    private Boolean isDelayed;

    private long delayedTime;

    @Indexed(expireAfterSeconds = 86400)
    private LocalDateTime timeCreated;

    private LocalDateTime deadLine;

    public Tracking(String id, long assignmentId, Boolean isComplete, Boolean isDelayed, long delayedTime, LocalDateTime timeCreated, LocalDateTime deadLine) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.isComplete = isComplete;
        this.isDelayed = isDelayed;
        this.delayedTime = delayedTime;
        this.timeCreated = timeCreated;
        this.deadLine = deadLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Tracking{" +
                "id=" + id +
                ", assignmentId=" + assignmentId +
                ", isComplete=" + isComplete +
                ", isDelayed=" + isDelayed +
                ", delayedTime=" + delayedTime +
                ", timeCreated=" + timeCreated +
                ", deadLine=" + deadLine +
                '}';
    }

    public Tracking() {
    }
}
