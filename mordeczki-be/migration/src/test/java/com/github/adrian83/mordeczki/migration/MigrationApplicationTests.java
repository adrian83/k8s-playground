package com.github.adrian83.mordeczki.migration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"DB_URL=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
		   "DB_USERNAME=localhost",
		   "DB_PASSWORD=secret",
		   "DB_DRIVER=org.h2.Driver"})
class MigrationApplicationTests {

	@Test
	void contextLoads() {
	}

}
