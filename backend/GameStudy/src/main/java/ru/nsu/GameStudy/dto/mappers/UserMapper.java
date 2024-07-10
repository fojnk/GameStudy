package ru.nsu.GameStudy.dto.mappers;

import org.springframework.stereotype.Component;
import ru.nsu.GameStudy.dto.UserDTO;
import ru.nsu.GameStudy.models.User;

@Component
public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getFathersName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getRole()
        );
    }
}