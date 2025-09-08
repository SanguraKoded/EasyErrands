package com.sangura.Assignment_Service.dtos;

import java.util.List;

public class TaskerSavedDto {

    private String username;

    private String email;

    private String location;

    private int errandsCompleted;

    private List<Long> activeErrandIds;

    private int ratings;

    public TaskerSavedDto(String username, String email, String location, int errandsCompleted, List<Long> activeErrandIds, int ratings) {
        this.username = username;
        this.email = email;
        this.location = location;
        this.errandsCompleted = errandsCompleted;
        this.activeErrandIds = activeErrandIds;
        this.ratings = ratings;
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

    public int getErrandsCompleted() {
        return errandsCompleted;
    }

    public void setErrandsCompleted(int errandsCompleted) {
        this.errandsCompleted = errandsCompleted;
    }

    public List<Long> getActiveErrandIds() {
        return activeErrandIds;
    }

    public void setActiveErrandIds(List<Long> activeErrandIds) {
        this.activeErrandIds = activeErrandIds;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public TaskerSavedDto() {
    }

    @Override
    public String toString() {
        return "TaskerSavedDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", errandsCompleted=" + errandsCompleted +
                ", activeErrandIds=" + activeErrandIds +
                ", ratings=" + ratings +
                '}';
    }
}

