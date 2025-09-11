package com.sangura.Errand_Service;

import com.sangura.Errand_Service.entities.Errand;
import com.sangura.Errand_Service.repos.ErrandsRepo;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers // enables Testcontainers lifecycle
@SpringBootTest
class ErrandServiceApplicationTests {


	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test");

	@DynamicPropertySource
	static void overrideProps(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private ErrandsRepo repo;

	@Test
	void testSaveAndFindErrand() {
		Errand e = new Errand();
		e.setDescription("Buy Milk");
		repo.save(e);

		assertThat(repo.findAll()).extracting("description").contains("Buy Milk");
	}
}
