package com.github.adrian83.mordeczki.auth.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.Sets;

import com.github.adrian83.mordeczki.auth.AuthPostgreSQLContainer;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.model.entity.Account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

@DataJpaTest
@Testcontainers
public class AccountRepositoryTest {

  @Container AuthPostgreSQLContainer container = AuthPostgreSQLContainer.getInstance();

  @Autowired private AccountRepository userRepository;

  @Test
  public void shouldSaveAccount() {
    // given
    var userEmail = "johndoe@qwertyhgfdsa@com";
    var userPassHash = "abcd-efgh";
    var roleNames = Sets.newHashSet("UserRoleA", "UserRoleB");
    var roles =
        roleNames
            .stream()
            .map(name -> Role.builder().name(name).build())
            .collect(Collectors.toSet());
    
    var account = Account.builder()
    		.email(userEmail)
    		.passwordHash(userPassHash)
    		.credentialsExpired(false)
    		.expired(false)
    		.enabled(true)
    		.locked(false)
    		.roles(roles)
    		.build();

    // when
    var saved = userRepository.save(account);

    // then
    assertThat(saved.getEmail()).isEqualTo(userEmail);
    assertThat(saved.getPasswordHash()).isEqualTo(userPassHash);
    assertThat(saved.getRoles())
        .allSatisfy(role -> assertThat(role.getId()).isNotNull())
        .allSatisfy(role -> assertThat(roleNames).contains(role.getName()));
  }
}
