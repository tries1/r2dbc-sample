package com.example.r2dbc.service;

import com.example.r2dbc.model.User;
import com.example.r2dbc.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Mono<Long> count() {
        return userRepository.count();
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> addUser(User user) {
        log.info("{}", user);
        return userRepository.save(user);
    }

    public Flux<User> addUsers(Flux<User> users) {
        log.info("{}", users);
        return userRepository.saveAll(users);
    }

    public Mono<Void> deleteUser(Long id) {
        log.info("{}", id);
        return userRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }
}