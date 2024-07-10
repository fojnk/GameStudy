package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.UserDTO;
import ru.nsu.GameStudy.models.User;
import ru.nsu.GameStudy.repository.UserRepository;
import ru.nsu.GameStudy.requests.RegisterRequest;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public User createUser(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .fathersName(request.getFathersName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    public Optional<User> getUserByPhoneNumber(String phonenumber) {
        return userRepository.findByPhoneNumber(phonenumber);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
        userRepository.delete(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
