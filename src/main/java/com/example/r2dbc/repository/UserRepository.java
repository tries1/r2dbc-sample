package com.example.r2dbc.repository;

import com.example.r2dbc.model.User;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
}
