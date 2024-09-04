package ru.nsu.GameStudy.authentication.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.GameStudy.authentication.dto.UserDTO;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("refresh_token")
    private String refresh_token;

    private UserDTO user;
}