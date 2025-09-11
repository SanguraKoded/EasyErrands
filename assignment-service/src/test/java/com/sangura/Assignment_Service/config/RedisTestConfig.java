package com.sangura.Assignment_Service.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

public class RedisTestConfig {

    static final GenericContainer<?> redis =
            new GenericContainer<>("redis:7.2.3").withExposedPorts(6379);

    @BeforeAll
    static void startContainer() {
        redis.start();
    }

    @DynamicPropertySource
    static void overrideRedisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }
}
