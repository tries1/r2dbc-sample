package com.example.r2dbc.service;

import com.example.r2dbc.model.User;
import com.example.r2dbc.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
