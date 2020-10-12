package com.example.r2dbc.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("users")
public class User {
    private Long id;
    private String name;
}
