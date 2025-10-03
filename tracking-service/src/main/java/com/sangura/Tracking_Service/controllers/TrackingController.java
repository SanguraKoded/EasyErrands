package com.sangura.Tracking_Service.controllers;

import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;
import com.sangura.Tracking_Service.services.TrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }


    @PostMapping("/user/create")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<TrackingDto> createTracking(@RequestBody TrackingCreateDto trackingDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(trackingService.createTracking(trackingDto));

    }

    @PutMapping("/admin/complete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> completeTracking(@PathVariable ("id") Long id){
        return ResponseEntity.ok(trackingService.completeTracking(id));
    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<TrackingDto> updateTracking(@PathVariable ("id") Long id, @RequestBody TrackingCreateDto trackingDto){
        return ResponseEntity.ok(trackingService.updateTracking(id, trackingDto));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<TrackingDto> findTrackingById(@PathVariable ("id") Long id){
        return ResponseEntity.ok(trackingService.findTrackingById(id));
    }
}
