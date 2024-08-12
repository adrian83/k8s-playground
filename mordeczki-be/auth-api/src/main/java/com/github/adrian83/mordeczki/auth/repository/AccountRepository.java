package com.github.adrian83.mordeczki.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

}
