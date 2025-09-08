package com.sangura.Errand_Service.Dtos;

import com.sangura.Errand_Service.entities.Errand;

import java.util.List;

public class TaskerDto {

    private String username;

    private String email;

    private String location;

    public TaskerDto(String username, String email, String location) {
        this.username = username;
        this.email = email;
        this.location = location;
    }

    public TaskerDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "TaskerDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
