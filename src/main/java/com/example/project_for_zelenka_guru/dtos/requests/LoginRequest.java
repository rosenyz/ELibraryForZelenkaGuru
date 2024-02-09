package com.example.project_for_zelenka_guru.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO для регистрации
@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
