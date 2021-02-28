package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {}
