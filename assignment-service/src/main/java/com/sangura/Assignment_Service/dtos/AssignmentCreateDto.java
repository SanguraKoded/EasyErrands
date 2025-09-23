package com.sangura.Assignment_Service.dtos;


public class AssignmentCreateDto {
    private Long errandId;

    private Long taskerId;

    public AssignmentCreateDto(Long errandId, Long taskerId) {
        this.errandId = errandId;
        this.taskerId = taskerId;
    }


    public AssignmentCreateDto() {
    }

    @Override
    public String toString() {
        return "AssignmentDto{" +
                "errandId=" + errandId +
                ", taskerId=" + taskerId +
                '}';
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
