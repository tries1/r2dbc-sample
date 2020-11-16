package com.example.r2dbc.repository;

import com.example.r2dbc.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

//@ActiveProfiles("h2")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // @See https://github.com/spring-projects/spring-framework/issues/24226
    // @See https://dlsrb6342.github.io/2019/07/15/ThreadLocal-vs-InheritableThreadLocal/
    //@Transactional
    @Test
    @DisplayName("사용자 테이블 deleteAll 테스트")
    public void whenDeleteAllThen0IsExpected() {
        userRepository.deleteAll()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("사용자 테이블 findAll 테스트")
    public void whenFindAllThen3AreExpected() {
        userRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("사용자 테이블 추가 테스트")
    public void whenInserThen1AreExpected() {
        // given
        User user = User.builder()
                .name("test")
                .age(20)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        // when
        userRepository.deleteAll();
        Mono<User> u = userRepository.save(user);

        // then
        StepVerifier.create(u)
                .assertNext(it -> {
                    Assertions.assertEquals(user.getName(), it.getName());
                    Assertions.assertEquals(user.getAge(), it.getAge());
                    Assertions.assertEquals(user.getCreatedAt(), it.getCreatedAt());
                    Assertions.assertEquals(user.getUpdatedAt(), it.getUpdatedAt());
                })
                .verifyComplete();
    }
}