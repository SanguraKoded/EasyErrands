package com.sangura.Errand_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sangura.Errand_Service.repos")
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class ErrandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErrandServiceApplication.class, args);
	}

}
