package com.sangura.Tracking_Service.repos;

import com.sangura.Tracking_Service.entities.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepo extends JpaRepository<Tracking, Long> {
}
