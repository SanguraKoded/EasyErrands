package com.sangura.Assignment_Service;

import org.junit.jupiter.api.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisCacheIntegrationTest {


    @Service
    static class DummyService {
        private int counter = 0;

        @Cacheable("numbers")
        public int getNumber() {
            return ++counter;
        }
    }

    @Test
    void testRedisCache() {
        DummyService service = new DummyService();

        int first = service.getNumber();
        int second = service.getNumber();

        // The second call should return the cached value (same as first)
        assertThat(first).isEqualTo(second);
    }
}
