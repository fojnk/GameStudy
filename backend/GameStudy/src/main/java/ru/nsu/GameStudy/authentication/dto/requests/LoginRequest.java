package ru.nsu.GameStudy.authentication.dto.requests;

import lombok.Builder;

@Builder
public record LoginRequest (
        String email,
        String password
){
}