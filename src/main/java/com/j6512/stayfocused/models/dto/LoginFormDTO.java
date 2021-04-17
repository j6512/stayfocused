package com.j6512.stayfocused.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20, message = "invalid username, must be between 3 and 20 characters")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30, message = "invalid password, must be between 5 and 30 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

