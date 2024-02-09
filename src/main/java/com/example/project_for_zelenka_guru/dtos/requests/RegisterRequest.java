package com.example.project_for_zelenka_guru.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO для авторизации
@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
