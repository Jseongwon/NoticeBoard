package com.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Entity(name = "Profile")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {
    @Column(length = 64, nullable = true)
    private String name;

    @Column(length = 64, nullable = true)
    private String address;

    @Column(length = 64, nullable = true)
    private String telephoneNumber;

    @Email
    @Column(unique = true, length = 128, nullable = true)
    private String emailAddress;

    public Profile(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String address,
            @RequestParam(defaultValue = "") String telephoneNumber,
            @RequestParam(defaultValue = "") String emailAddress) {
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
    }
}
