package com.example.demo.DTOS;

import jakarta.validation.constraints.NotBlank;

public class UserRecordDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Getters e Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
