package com.j6512.stayfocused.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private UserProfile userProfile;

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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
