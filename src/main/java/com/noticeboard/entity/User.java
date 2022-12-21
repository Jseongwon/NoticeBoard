package com.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Entity(name = "User")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 128)
    private String name;

    @Column(unique = true, length = 128)
    private String emailAddress;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 256, nullable = false)
    private String salt;

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }
}
