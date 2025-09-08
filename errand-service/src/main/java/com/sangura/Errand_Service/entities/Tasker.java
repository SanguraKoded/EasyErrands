package com.sangura.Errand_Service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
public class Tasker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    private String location;

    private int errandsCompleted;

    private List<Long> activeErrandIds;

    private int ratings;

    public Tasker(long id, String username, String email, String location, int errandsCompleted, List<Long> activeErrands, int ratings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.location = location;
        this.errandsCompleted = errandsCompleted;
        this.activeErrandIds = activeErrands;
        this.ratings = ratings;
    }

    public Tasker() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Long> getActiveErrands() {
        return activeErrandIds;
    }

    public void setActiveErrands(List<Long> activeErrands) {
        this.activeErrandIds = activeErrands;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Tasker{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", errandsCompleted=" + errandsCompleted +
                ", activeErrands=" + activeErrandIds +
                ", ratings=" + ratings +
                '}';
    }
}
