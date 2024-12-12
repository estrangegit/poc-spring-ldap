package com.example.app.authentication.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Credentials {
    @NotNull(message = "Login can not be null")
    private final String login;
    
    @NotNull(message = "Password can not be null")
    private final String password;
}
