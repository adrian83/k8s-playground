package com.github.adrian83.mordeczki.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.adrian83.mordeczki.auth.model.command.User;

public interface UserRepository extends CrudRepository<User, String> {}
