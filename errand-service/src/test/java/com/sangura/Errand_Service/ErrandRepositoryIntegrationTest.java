package com.sangura.Errand_Service;

import com.sangura.Errand_Service.entities.Errand;
import com.sangura.Errand_Service.repos.ErrandsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class ErrandRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ErrandsRepo errandsRepo;

    @Test
    void saveAndFindErrand() {
        // Test logic to save and find an errand
        Errand errand = new Errand();
        errand.setDescription("Test Errand");
        errand.setLocation("Test Location");
        errand.setSpecialInstructions("Test Instructions");
        errand.setAssignedTasker(10L);
        errand.setDeadLine(LocalDateTime.now().plusHours(4));
        errand.setTimeCreated(LocalDateTime.now());
        errand.setTimeTaken(0L);
        errand.setLate(false);
        errand.setDone(false);
        errandsRepo.save(errand);

        Errand foundErrand = errandsRepo.findById(errand.getId()).orElse(null);
        assertThat(foundErrand).isNotNull();
        assertThat(foundErrand.getDescription()).isEqualTo("Test Errand");
    }
}
