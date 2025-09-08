package com.sangura.Tracking_Service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class Tracking {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long assignmentId;

    private Boolean isComplete;

    private Boolean isDelayed;

    private long delayedTime;

    private LocalDateTime timeCreated;

    private LocalDateTime deadLine;

    public Tracking(long id, long assignmentId, Boolean isComplete, Boolean isDelayed, long delayedTime, LocalDateTime timeCreated, LocalDateTime deadLine) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.isComplete = isComplete;
        this.isDelayed = isDelayed;
        this.delayedTime = delayedTime;
        this.timeCreated = timeCreated;
        this.deadLine = deadLine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
