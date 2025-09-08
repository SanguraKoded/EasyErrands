package com.sangura.Assignment_Service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long errandId;

    private Long taskerId;

    public Assignment() {
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", errandId=" + errandId +
                ", taskerId=" + taskerId +
                ", createdAt=" + createdAt +
                '}';
    }

    public Assignment(Long id, Long errandId, Long taskerId, LocalDateTime createdAt) {
        this.id = id;
        this.errandId = errandId;
        this.taskerId = taskerId;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private LocalDateTime createdAt;


}
