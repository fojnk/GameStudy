package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.authentication.services.AuthService;
import ru.nsu.GameStudy.authentication.services.JwtService;
import ru.nsu.GameStudy.authentication.services.UserService;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Личный аккаунт", description="Управление аккаунтом пользователя.")
public class AccountController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("/my-account")
    @Operation(description = "Личный кабинет.")
    public UserDTO getUserAccount(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Cannot authorize");
        }
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        return userService.getUserByEmail(username);
        //return authService.authWithToken(token);
    }
}
