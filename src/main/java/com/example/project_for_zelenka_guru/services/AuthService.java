package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.components.JwtCore;
import com.example.project_for_zelenka_guru.dtos.requests.LoginRequest;
import com.example.project_for_zelenka_guru.dtos.requests.RegisterRequest;
import com.example.project_for_zelenka_guru.models.User;
import com.example.project_for_zelenka_guru.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    // внедрение зависимостей ( dependency injection )
    private final UserService userService;
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    // регистрация пользователя
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже существует!");
        }

        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // шифрование пароля
        user.getRoles().add(Role.ROLE_USER);

        userService.save(user);

        return ResponseEntity.ok("Вы успешно зарегистрировались!");
    }

    // авторизация
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(userService.loadUserByUsername(loginRequest.getUsername()));

        return ResponseEntity.ok(token);
    }
}