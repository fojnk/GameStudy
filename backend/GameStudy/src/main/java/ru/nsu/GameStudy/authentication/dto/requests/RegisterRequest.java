package ru.nsu.GameStudy.authentication.dto.requests;

import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.authentication.models.Role;

@Builder
@Getter
public class RegisterRequest {
        private String name;
        private String surname;
        private String fathersName;
        private String password;
        private String email;
        private String phoneNumber;
        private Role role;
}