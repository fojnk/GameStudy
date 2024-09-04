package ru.nsu.GameStudy.authentication.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.authentication.dto.NotificationDTO;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.mappers.NotificationMapper;
import ru.nsu.GameStudy.mappers.UserMapper;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.authentication.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationMapper notificationMapper;

    @Transactional
    public UserDTO createUser(UserDTO request) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(request.getEmail())
                || (request.getPhoneNumber() != null && userRepository.existsByPhoneNumber(request.getPhoneNumber()))) {
            throw new UserAlreadyExistsException();
        }
        log.info("CREATE user, request: {name: {}, surname: {}, " +
                "fathersName: {}, email: {}, phoneNumber: {}}",
                request.getName(), request.getSurname(), request.getFathersName(),
                request.getEmail(), request.getPhoneNumber());

        if (request.getPhoneNumber()!= null && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.error("UPDATE failed: phone number {} is already used by user {}",
                    request.getPhoneNumber(),
                    userRepository.findByPhoneNumber(request.getPhoneNumber()).get().getId());
            throw new UserAlreadyExistsException("phone number is already taken by another user");
        }

        User user = User.builder()
                .password(request.getPassword())
                .build();
        userMapper.updateUserFromDTO(user, request);

        return save(user);
    }

    public List<UserDTO> getAllUsers() {
        log.info("GET all users");
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO getUserDTO(Long userId) {
        return userMapper.toDTO(getUser(userId));
    }

    public UserDTO getUserByPhoneNumber(String phoneNumber) {
        log.info("GET user with phone number: {}", phoneNumber);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            log.error("user with phoneNumber: {} not found", phoneNumber);
            throw new NotFoundException("user not found");
        }
        else {
            return userMapper.toDTO(user.get());
        }
    }

    public UserDTO getUserByEmail(String email) {
        log.info("GET user with email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.error("user with email: {} not found", email);
            throw new NotFoundException("user not found");
        }
        else {
            return userMapper.toDTO(user.get());
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("DELETE user with id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            log.error("user with id: {} not found", id);
            throw new NotFoundException("user not found");
        }
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO request) throws UserAlreadyExistsException {
        log.info("UPDATE user with id: {}, request: {name: {}, surname: {}, " +
                        "fathersName: {}, email: {}, phoneNumber: {}}", id, request.getName(),
                request.getSurname(), request.getFathersName(), request.getPhoneNumber(), request.getEmail());
        User user = getUser(id);

        if (request.getPhoneNumber()!= null && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.error("UPDATE failed: phone number {} is already used by user {}",
                    request.getPhoneNumber(),
                    userRepository.findByPhoneNumber(request.getPhoneNumber()).get().getId());
            throw new UserAlreadyExistsException("phone number is already taken by another user");
        }

        userMapper.updateUserFromDTO(user, request);

        return save(user);
    }

    @Transactional
    public UserDTO updateUserPassword(Long id, String password) {
        User user = getUser(id);

        user.setPassword(passwordEncoder.encode(password));
        return save(user);
    }

    @Transactional
    public List<NotificationDTO> getReceivedNotifications(Long userId) {
        log.info("GET received notifications for user with id: {}", userId);
        User user = getUser(userId);
        return user.getReceivedNotifications().stream()
                .map(notificationMapper::toDTO).toList();
    }

    @Transactional
    public List<NotificationDTO> getSentNotifications(Long userId) {
        log.info("GET notifications sent by user with id: {}", userId);
        User user = getUser(userId);
        return user.getSentNotifications().stream()
                .map(notificationMapper::toDTO).toList();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    protected UserDTO save(User user) {
        return userMapper.toDTO(userRepository.save(user));
    }

    protected User getUser(Long id) {
        log.info("GET user by id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.error("user with id: {} not found", id);
            throw new NotFoundException("user not found");
        }
        else {
            return user.get();
        }
    }
}
