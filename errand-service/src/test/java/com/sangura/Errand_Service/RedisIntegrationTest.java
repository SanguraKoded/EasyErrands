package com.sangura.Errand_Service;


import com.sangura.Errand_Service.entities.Errand;
import com.sangura.Errand_Service.repos.ErrandsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class RedisIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ErrandsRepo errandsRepo;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testRedisCacheStoresData() {
        // 1. Save an errand
        Errand errand = new Errand();
        errand.setDescription("Redis Test Errand");
        errand.setLocation("Test Location");
        errand.setSpecialInstructions("Cache Test");
        errand.setAssignedTasker(5L);
        errand.setDeadLine(LocalDateTime.now().plusHours(3));
        errand.setTimeCreated(LocalDateTime.now());
        errand.setTimeTaken(0L);
        errand.setLate(false);
        errand.setDone(false);
        errandsRepo.save(errand);

        // 2. Put value into cache manually
        cacheManager.getCache("errands").put("testKey", errand);

        // 3. Retrieve from cache
        Errand cachedErrand = cacheManager.getCache("errands").get("testKey", Errand.class);

        // 4. Assert it worked
        assertThat(cachedErrand).isNotNull();
        assertThat(cachedErrand.getDescription()).isEqualTo("Redis Test Errand");
    }
}

