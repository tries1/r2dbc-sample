package com.example.r2dbc.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    Long id;
    String name;
    Integer age;

    @Builder
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
