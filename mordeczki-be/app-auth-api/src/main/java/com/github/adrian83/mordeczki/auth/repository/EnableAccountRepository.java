package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.EnableAccount;

public interface EnableAccountRepository extends PagingAndSortingRepository<EnableAccount, String> {

}
