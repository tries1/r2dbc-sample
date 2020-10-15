package com.example.r2dbc.service;

import com.example.r2dbc.model.User;
import com.example.r2dbc.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void generate() {
        this.count()
        .filter(count -> count < 10_000)
        .map(count -> this.generateSampleUser()
                    .buffer(100)
                    .flatMap(users -> addUsers(Flux.fromIterable(users)).subscribeOn(Schedulers.elastic()))
                    .limitRequest(10_000)
                    .subscribe()
        )
        .subscribe();

    }

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

    public Flux<User> generateSampleUser() {
        return Flux.interval(Duration.ofMillis(10))
                .map(i -> new User("user" + i));
    }
}
