package ru.nsu.GameStudy.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponseDTO {
        @JsonProperty("access_token")
        String accessToken;

        @JsonProperty("refresh_token")
        String refreshToken;

        @JsonProperty("message")
        String message;
}