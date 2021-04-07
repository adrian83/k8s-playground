package com.github.adrian83.mordeczki.auth.repository;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.adrian83.mordeczki.auth.AuthPostgreSQLContainer;
import com.github.adrian83.mordeczki.auth.model.entity.Role;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@Testcontainers
public class RoleRepositoryTest {
	
	@Container
	AuthPostgreSQLContainer container = AuthPostgreSQLContainer.getInstance();
	
	@Autowired
	private RoleRepository userRoleRepository;


	@Test
	public void shouldSaveRole() {
		// given 
		var roleName = "Role A";
		var role = Role.builder().name(roleName).build();
		
		// when
		Role saved = userRoleRepository.save(role);
		
		// then
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo(roleName);
	}
	
	@Test
	public void shouldFindRoleById() {
		// given 
		var role = Role.builder().name("Role B").build();
		
		Role saved = userRoleRepository.save(role);
		
		// when
		Optional<Role> maybeFound = userRoleRepository.findById(saved.getId());
		
		// then
		assertThat(maybeFound).isNotEmpty();
		
		Role found = maybeFound.get();
		assertThat(found.getId()).isEqualTo(saved.getId());
		assertThat(found.getName()).isEqualTo(saved.getName());
	}
	
	@Test
	public void shouldPaginateRoles() {
		// given 
		
		Role saved1 = userRoleRepository.save(Role.builder().name("Role C").build());
		Role saved2 = userRoleRepository.save(Role.builder().name("Role D").build());
		Role saved3 = userRoleRepository.save(Role.builder().name("Role E").build());
		var roles = Lists.list(saved1, saved2, saved3);
		
		// when
		Page<Role> page1 = userRoleRepository.findAll(PageRequest.of(0, 2));
		Page<Role> page2 = userRoleRepository.findAll(PageRequest.of(1, 2));
		
		// then
		assertThat(roles).containsAll(page1.getContent());
		assertThat(roles).containsAll(page2.getContent());
		assertThat(page1.getNumberOfElements()).isEqualTo(2);
		assertThat(page2.getNumberOfElements()).isGreaterThanOrEqualTo(1);
		assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
		assertThat(page1.getTotalElements()).isGreaterThanOrEqualTo(3);
	}
	
}
