package com.sangura.Tracking_Service.client;
import com.sangura.Tracking_Service.dtos.AssignmentDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assignment-service")
public interface AssignmentServiceClient {

    @GetMapping("/assignment/id")
    AssignmentDto findAssignmentById(@PathVariable ("id") Long id);
}
