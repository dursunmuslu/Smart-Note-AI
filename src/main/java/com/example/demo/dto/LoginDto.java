package com.example.demo.dto;

import lombok.Data;

@Data

public class LoginDto {
    private String email;
    private String password;

    // Default Constructor
    public LoginDto() {}

    // Parametreli Constructor
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter ve Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}