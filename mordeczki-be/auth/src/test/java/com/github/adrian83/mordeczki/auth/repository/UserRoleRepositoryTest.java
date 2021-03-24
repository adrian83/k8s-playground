package com.github.adrian83.mordeczki.auth.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.adrian83.mordeczki.auth.AuthPostgreSQLContainer;
import com.github.adrian83.mordeczki.auth.model.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class UserRoleRepositoryTest {
	
	@Container
	AuthPostgreSQLContainer g = AuthPostgreSQLContainer.getInstance();
	
	@Autowired
	private UserRoleRepository userRoleRepository;


	
	@Test
	public void saveRole() {
		Role role = Role.builder().name("TEST").build();
		
		Role saved = userRoleRepository.save(role);
		
		Assertions.assertThat(saved.getId()).isNotNull();
	}
	
	
}
