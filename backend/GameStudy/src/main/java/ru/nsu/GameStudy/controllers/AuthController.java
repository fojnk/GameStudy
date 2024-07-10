package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.models.User;
import ru.nsu.GameStudy.requests.LoginRequest;
import ru.nsu.GameStudy.requests.RegisterRequest;
import ru.nsu.GameStudy.response.AuthResponse;
import ru.nsu.GameStudy.services.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name="Авторизация и Аутентификация", description="Создание новых пользователей, разрешение доступа.")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.auth(loginRequest));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new AuthResponse(
                    null, null, e.toString()
            ));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(authService.register(registerRequest));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(
                    new AuthResponse(null, null, e.toString())
            );
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refresh( HttpServletRequest request,  HttpServletResponse response ) {
        return authService.refreshToken(request, response);
    }
}