package com.sangura.Assignment_Service.client;

import com.sangura.Assignment_Service.dtos.ErrandSavedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "errand-service")
public interface ErrandServiceClient {

    @GetMapping("/errands/{id}")
    ErrandSavedDto findErrandById(@PathVariable ("id") Long id);

}
