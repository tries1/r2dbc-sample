package com.example.r2dbc.controller;

import com.example.r2dbc.model.User;
import com.example.r2dbc.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @GetMapping("users")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(value = "users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .thenReturn(id);
    }

}