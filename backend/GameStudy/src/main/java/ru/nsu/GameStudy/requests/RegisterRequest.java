package ru.nsu.GameStudy.requests;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.GameStudy.models.Role;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String surname;
    private String fathersName;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;
}