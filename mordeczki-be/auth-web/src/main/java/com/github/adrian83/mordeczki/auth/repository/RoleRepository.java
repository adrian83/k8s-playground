package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {}
