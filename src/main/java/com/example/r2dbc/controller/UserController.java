package com.example.r2dbc.controller;

import com.example.r2dbc.model.User;
import com.example.r2dbc.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public Flux<User> findAll(){
        return userService.findAll();
    }
}
