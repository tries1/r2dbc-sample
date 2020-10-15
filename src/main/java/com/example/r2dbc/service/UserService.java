package com.example.r2dbc.service;

import com.example.r2dbc.model.User;
import com.example.r2dbc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import io.r2dbc.spi.ConnectionFactory;
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
    private final ConnectionFactory connectionFactory;

    //@PostConstruct
    public void generate() {
        this.generateSampleUser()
                .buffer(10)
                .flatMap(users ->
                        Flux.fromIterable(users)
                                .map(user -> addUser(user).subscribe())
                                .subscribeOn(Schedulers.parallel())
                )
                //.limitRequest(100)
                .subscribe();
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> addUser(User user) {
        log.info("{}", user);
        return userRepository.save(user);
    }

    public Mono<Void> deleteUser(Long id) {
        log.info("{}", id);
        return userRepository.deleteById(id);
    }

    public Flux<User> generateSampleUser() {
        return Flux.interval(Duration.ofMillis(10))
                .map(i -> new User("user" + i))
                .doOnNext(System.out::println);
    }
}
