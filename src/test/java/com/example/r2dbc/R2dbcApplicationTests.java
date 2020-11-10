package com.example.r2dbc;

import com.example.r2dbc.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

@SpringBootTest
class R2dbcApplicationTests {

	@Autowired
	private UserRepository userRepository;

    @Test
    public void whenDeleteAll_then0IsExpected() {
        userRepository.deleteAll()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void whenInsert6_then6AreExpected() {
        userRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(6)
                .verifyComplete();
    }

}
