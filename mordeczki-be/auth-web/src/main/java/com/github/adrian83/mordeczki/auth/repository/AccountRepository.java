package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.github.adrian83.mordeczki.auth.model.entity.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
}
