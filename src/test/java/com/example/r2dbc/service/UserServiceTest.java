package com.example.r2dbc.service;

import com.example.r2dbc.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("test")
                .age(20)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        userService.addUser(user);
        userService.addUser(user);
        userService.addUser(user);
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll();
    }

    @Test
    void count() {
        //when
        Mono<Long> userCount = userService.count();

        //then
        userCount.as(StepVerifier::create)
                .assertNext(count -> Assertions.assertEquals(count, 3))
                .verifyComplete();
    }

    @Test
    void findAll() {
        //when
        Flux<User> users = userService.findAll();

        //then
        users.as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void addUser() {
        User user = User.builder()
                .name("test")
                .age(20)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        userService.addUser(user);
    }

    @Disabled
    @Test
    void addUsers() {
        //given
        Flux<User> users = Flux.interval(Duration.ofMillis(10))
                .map(i -> User.builder()
                        .name("test" + i)
                        .age(i.intValue())
                        .updatedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()
                )
                .limitRequest(10)
                .buffer()
                .flatMap(it -> Flux.fromIterable(it));

        //when
        Flux<User> result = userService.addUsers(users);

        //then
        result.as(StepVerifier::create)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    void deleteUser() {
    }
}