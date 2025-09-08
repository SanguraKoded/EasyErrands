package com.sangura.common.events.DTOs;

import com.sangura.common.events.enums.ErrandEventType;

import java.time.Instant;

public class ErrandEvent {

    private ErrandEventType eventType;

    private Long errandId;

    private String description;

    private String payload;

    private Instant occuredAt = Instant.now();

    public ErrandEvent() {
    }

    public ErrandEventType getEventType() {
        return eventType;
    }

    public void setEventType(ErrandEventType eventType) {
        this.eventType = eventType;
    }

    public Long getErrandId() {
        return errandId;
    }

    public void setErrandId(Long errandId) {
        this.errandId = errandId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Instant getOccuredAt() {
        return occuredAt;
    }

    public void setOccuredAt(Instant occuredAt) {
        this.occuredAt = occuredAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ErrandEvent(ErrandEventType eventType, Long errandId, String description, String payload, Instant occuredAt) {
        this.eventType = eventType;
        this.errandId = errandId;
        this.description = description;
        this.payload = payload;
        this.occuredAt = occuredAt;
    }
}
