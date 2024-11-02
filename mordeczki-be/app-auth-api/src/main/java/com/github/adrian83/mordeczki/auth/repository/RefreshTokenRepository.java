package com.github.adrian83.mordeczki.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.adrian83.mordeczki.auth.model.entity.RefreshToken;

public interface RefreshTokenRepository extends PagingAndSortingRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmail(String email);

}
