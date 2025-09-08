package com.sangura.Tracking_Service.services;

import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;

public interface TrackingService {

    TrackingDto createTracking(TrackingCreateDto trackingDto);

    TrackingDto updateTracking(Long id, TrackingCreateDto trackingDto);

    String completeTracking(Long id);

    TrackingDto findTrackingById(Long id);
}
