package com.example.r2dbc.repository;

import com.example.r2dbc.model.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
