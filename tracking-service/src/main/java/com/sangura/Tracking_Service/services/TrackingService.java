package com.sangura.Tracking_Service.services;

import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;

public interface TrackingService {

    TrackingDto createTracking(TrackingCreateDto trackingDto);

    TrackingDto updateTracking(String id, TrackingCreateDto trackingDto);

    String completeTracking(String id);

    TrackingDto findTrackingById(String id);
}
