package com.erich.dev.course.entity.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;
}
