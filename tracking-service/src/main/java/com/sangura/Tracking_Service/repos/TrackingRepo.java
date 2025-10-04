package com.sangura.Tracking_Service.repos;

import com.sangura.Tracking_Service.entities.Tracking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackingRepo extends MongoRepository<Tracking, String> {
}
