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
        User user1 = User.builder().name("test1").age(10).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
        User user2 = User.builder().name("test2").age(20).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
        User user3 = User.builder().name("test3").age(30).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();

        userService.deleteAll()
        .and(userService.addUsers(Flux.just(user1, user2, user3)))
        .subscribe();
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll().subscribe();
    }

    @Test
    void count() {
        // when & then
        userService.count().as(StepVerifier::create)
                .assertNext(count -> Assertions.assertEquals(count, 3))
                .verifyComplete();
    }

    @Test
    void findAll() {
        System.out.println("findAll");
        // when & then
        userService.findAll().as(StepVerifier::create)
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

        // when & then
        StepVerifier.create(userService.addUser(user))
                .assertNext(it -> {
                    Assertions.assertEquals(user.getName(), it.getName());
                    Assertions.assertEquals(user.getAge(), it.getAge());
                    Assertions.assertEquals(user.getCreatedAt(), it.getCreatedAt());
                    Assertions.assertEquals(user.getUpdatedAt(), it.getUpdatedAt());
                })
                .verifyComplete();
    }

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

        // when & then
        userService.addUsers(users).as(StepVerifier::create)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    void deleteUser() {
    }
}