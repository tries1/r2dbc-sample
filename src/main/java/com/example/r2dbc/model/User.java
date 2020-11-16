package com.example.r2dbc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;
    private String name;
    private Integer age;
    private String profilePictureUrl;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @Builder
    public User(String name, Integer age, String profilePictureUrl, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.profilePictureUrl = profilePictureUrl;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
