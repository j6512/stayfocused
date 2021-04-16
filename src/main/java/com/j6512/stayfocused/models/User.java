package com.j6512.stayfocused.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    private String username;
    private String passwordHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = encoder.encode(password);
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, passwordHash);
    }

}
