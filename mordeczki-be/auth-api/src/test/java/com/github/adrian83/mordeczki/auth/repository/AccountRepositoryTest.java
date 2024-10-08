package com.github.adrian83.mordeczki.auth.repository;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.Sets;

import com.github.adrian83.mordeczki.auth.AuthPostgreSQLContainer;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.model.entity.Role;

@DataJpaTest
@Testcontainers
public class AccountRepositoryTest {

    @Container
    AuthPostgreSQLContainer container = AuthPostgreSQLContainer.getInstance();

    @Autowired
    private AccountRepository userRepository;

    @Test
    public void shouldSaveAccount() {
        // given
        var userEmail = "johndoe@qwertyhgfdsa@com";
        var userPassHash = "abcd-efgh";
        var roleNames = Sets.newHashSet("UserRoleA", "UserRoleB");
        var roles = roleNames
                .stream()
                .map(name -> new Role(name))
                .collect(Collectors.toSet());

        var account = new Account(1L, userEmail, userPassHash, false, false, true, false, roles);

        // when
        var saved = userRepository.save(account);

        // then
        assertThat(saved.getId()).isGreaterThan(0L);
        assertThat(saved.getEmail()).isEqualTo(userEmail);
        assertThat(saved.getPasswordHash()).isEqualTo(userPassHash);
        assertThat(saved.getRoles())
                .allSatisfy(role -> assertThat(role.getId()).isNotNull())
                .allSatisfy(role -> assertThat(roleNames).contains(role.getName()));
    }
}
