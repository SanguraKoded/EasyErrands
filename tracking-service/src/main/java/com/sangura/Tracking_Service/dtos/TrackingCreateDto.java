package com.sangura.Tracking_Service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



public class TrackingCreateDto {
    private long assignmentId;

    private LocalDateTime deadLine;


    public TrackingCreateDto() {
    }
    public TrackingCreateDto(long assignmentId, LocalDateTime deadLine) {
        this.assignmentId = assignmentId;
        this.deadLine = deadLine;
    }
    public long getAssignmentId() {
        return assignmentId;
    }
    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }
    public LocalDateTime getDeadLine() {
        return deadLine;   }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;}

    @Override
    public String toString() {
        return "TrackingCreateDto{" +
                "assignmentId=" + assignmentId +
                ", deadLine=" + deadLine +
                '}';
    }
}
