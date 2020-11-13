package com.example.r2dbc;

import com.example.r2dbc.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import reactor.test.StepVerifier;

//@ActiveProfiles("h2")
@SpringBootTest
class R2dbcApplicationTests {

	@Autowired
	private UserRepository userRepository;

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

}
