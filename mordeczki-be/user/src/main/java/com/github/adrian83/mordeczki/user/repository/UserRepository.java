package com.github.adrian83.mordeczki.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.adrian83.mordeczki.user.model.command.User;

public interface UserRepository extends CrudRepository<User, String> {}
