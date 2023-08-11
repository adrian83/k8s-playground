package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.ResetPassword;

public interface ResetPasswordRepository extends PagingAndSortingRepository<ResetPassword, String> {
}
