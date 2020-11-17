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
        userService.deleteAll();

        User user1 = User.builder().name("test1").age(10).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
        User user2 = User.builder().name("test2").age(20).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();
        User user3 = User.builder().name("test3").age(30).updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();

        userService.addUsers(Flux.just(user1, user2, user3));
        System.out.println("UserServiceTest setUp : user count : " + userService.findAll().count().block());
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
        System.out.println("findAll");
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

        Mono<User> newUser = userService.addUser(user);

        // then
        StepVerifier.create(newUser)
                .assertNext(it -> {
                    Assertions.assertEquals(user.getName(), it.getName());
                    Assertions.assertEquals(user.getAge(), it.getAge());
                    Assertions.assertEquals(user.getCreatedAt(), it.getCreatedAt());
                    Assertions.assertEquals(user.getUpdatedAt(), it.getUpdatedAt());
                })
                .verifyComplete();
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