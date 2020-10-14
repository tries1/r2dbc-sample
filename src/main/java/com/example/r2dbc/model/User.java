package com.example.r2dbc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Table("users")
public class User {
    @Id
    private Long id;

    private String name;

    public User(String name) {
        this.name = name;
    }
}
