package com.sangura.Assignment_Service.client;

import com.sangura.Assignment_Service.dtos.TaskerSavedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tasker-service")
public interface TaskerServiceClient {

    @GetMapping("/tasker/{id}")
    TaskerSavedDto findTaskerById(@PathVariable  ("id") Long id);

    TaskerSavedDto findTaskerByUsername(@PathVariable String username);
}
