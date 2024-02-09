package com.example.project_for_zelenka_guru.controllers;

import com.example.project_for_zelenka_guru.dtos.requests.LoginRequest;
import com.example.project_for_zelenka_guru.dtos.requests.RegisterRequest;
import com.example.project_for_zelenka_guru.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    // внедрение зависимости AuthService ( dependency injection )
    // логика всех эндпоинтов описана в AuthService
    private final AuthService authService;

    // эндпоинт для регистрации учетной записи
    // @Valid - смотрите RegisterRequest
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    // эндпоинт для входа в существующую учетную запись
    // @Valid - смотрите LoginRequest
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }
}
