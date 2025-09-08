package com.sangura.Errand_Service.Dtos;

import java.time.LocalDateTime;

public class ErrandSavedDto {
    private long id;

    private String description;

    private String specialInstructions;

    private LocalDateTime timeCreated;

    private LocalDateTime deadLine;

    private String location;

    private Boolean isDone;

    private long timeTaken;

    private long assignedTasker;

    private Boolean isLate;

    public ErrandSavedDto(long id, String description, String specialInstructions, LocalDateTime timeCreated, LocalDateTime deadLine, String location, Boolean isDone, long timeTaken, long assignedTasker, Boolean isLate) {
        this.id = id;
        this.description = description;
        this.specialInstructions = specialInstructions;
        this.timeCreated = timeCreated;
        this.deadLine = deadLine;
        this.location = location;
        this.isDone = isDone;
        this.timeTaken = timeTaken;
        this.assignedTasker = assignedTasker;
        this.isLate = isLate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long getAssignedTasker() {
        return assignedTasker;
    }

    public void setAssignedTasker(long assignedTasker) {
        this.assignedTasker = assignedTasker;
    }

    public Boolean getLate() {
        return isLate;
    }

    public void setLate(Boolean late) {
        isLate = late;
    }

    @Override
    public String toString() {
        return "ErrandDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", specialInstructions='" + specialInstructions + '\'' +
                ", timeCreated=" + timeCreated +
                ", deadLine=" + deadLine +
                ", location='" + location + '\'' +
                ", isDone=" + isDone +
                ", timeTaken=" + timeTaken +
                ", assignedTasker=" + assignedTasker +
                ", isLate=" + isLate +
                '}';
    }

    public ErrandSavedDto() {
    }
}
