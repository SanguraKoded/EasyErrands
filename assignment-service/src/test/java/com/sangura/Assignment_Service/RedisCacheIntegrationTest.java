package com.sangura.Assignment_Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisCacheIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7.2.3")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void overrideRedisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    DummyService service;

    @Service
    static class DummyService {
        private int counter = 0;

        @Cacheable("numbers")
        public int getNumber() {
            return ++counter;
        }
    }

    @Test
    void testRedisCacheWorks() {
        int first = service.getNumber();
        int second = service.getNumber();

        // Second call must be cached
        assertThat(first).isEqualTo(second);
    }
}
