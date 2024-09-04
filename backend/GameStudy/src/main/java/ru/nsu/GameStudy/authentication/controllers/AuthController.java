package ru.nsu.GameStudy.authentication.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.authentication.dto.requests.LoginRequest;
import ru.nsu.GameStudy.authentication.dto.requests.RegisterRequest;
import ru.nsu.GameStudy.authentication.dto.response.AuthResponse;
import ru.nsu.GameStudy.authentication.services.AuthService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="Авторизация и Аутентификация", description="Создание новых пользователей, разрешение доступа.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @Operation(description = "Логин.")
    public AuthResponse authUser(@RequestBody LoginRequest loginRequest) {
        return authService.auth(loginRequest);
    }

    @PostMapping("/sign-up")
    @Operation(description = "Регистрация пользователя.")
    public AuthResponse registerUser(@RequestBody RegisterRequest registerRequest)
            throws UserAlreadyExistsException, ValueAlreadyExistsException, ParseException {
        return authService.register(registerRequest);
    }

    @PostMapping("/refresh_token")
    @Operation(description = "Обновление токена.")
    public ResponseEntity<?> refresh( HttpServletRequest request,  HttpServletResponse response ) {
        return authService.refreshToken(request, response);
    }
}