package com.sangura.Tracking_Service.dtos;

import java.time.LocalDateTime;

public class TrackingCreateDto {
    private LocalDateTime deadLine;

    public TrackingCreateDto(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return "TrackingCreateDto{" +
                "deadLine=" + deadLine +
                '}';
    }

    public TrackingCreateDto() {
    }
}
