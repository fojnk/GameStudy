package ru.nsu.GameStudy.authentication.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.authentication.dto.NotificationDTO;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;
import ru.nsu.GameStudy.authentication.services.NotificationService;
import ru.nsu.GameStudy.authentication.services.UserService;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name="Пользователи", description="Общие эндпоинты для работы с пользователями.")
public class UserController {
    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    @Operation(description = "Получение пользователя по id.")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserDTO(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление пользователя по id.")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO request)
            throws UserAlreadyExistsException {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Обновление пользователя по id.")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    @Operation(description = "Создание пользователя.")
    public UserDTO createUser(@RequestBody UserDTO request)
            throws UserAlreadyExistsException {
        return userService.createUser(request);
    }

    @GetMapping("/")
    @Operation(description = "Получение списка всех пользователей.")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}/notifications")
    @Operation(description = "Отправка уведомлений пользователем.")
    public ResponseEntity sendNotification(
            @PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        NotificationDTO notification = notificationService.createNotification(notificationDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/notifications/inbox")
    @Operation(description = "Получение уведомлений, отправленных пользователю.")
    public List<NotificationDTO> getUserReceivedNotifications(
            @PathVariable Long id, @RequestParam(name = "sender-id", required = false) Long senderId) {
        if (senderId == null) {
            return userService.getReceivedNotifications(id);
        }
        else {
            return notificationService.getNotificationsBySenderAndRecipient(senderId, id);
        }
    }

    @GetMapping("/{id}/notifications/sent")
    @Operation(description = "Получение уведомлений, отправленных пользователем.")
    public List<NotificationDTO> getUserSentNotifications(
            @PathVariable Long id, @RequestParam(name = "recipient-id", required = false) Long recipientId) {
        if (recipientId == null) {
            return userService.getSentNotifications(id);
        }
        else {
            return notificationService.getNotificationsBySenderAndRecipient(id, recipientId);
        }
    }

    @PutMapping("/{id}/notifications/confirm")
    @Operation(description = "Подтверждение уведомления, полученного пользователем.")
    public ResponseEntity<String> confirmNotification(
            @PathVariable Long id, @RequestParam(name = "notification-id", required = false) Long notificationId) {
        if (notificationService.confirmNotificationByRecipient(notificationId, id)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("notification already confirmed", HttpStatus.BAD_REQUEST);
    }
}