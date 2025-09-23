package com.sangura.Assignment_Service.dtos;

import java.time.LocalDateTime;

public class AssignmentDto {

    private Long id;

    private Long errandId;

    private Long taskerId;

    public AssignmentDto() {
    }

    public AssignmentDto(Long id, Long errandId, Long taskerId) {
        this.id = id;
        this.errandId = errandId;
        this.taskerId = taskerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AssignmentDto{" +
                "id=" + id +
                ", errandId=" + errandId +
                ", taskerId=" + taskerId +
                ", createdAt=" + createdAt +
                '}';
    }

    private LocalDateTime createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
