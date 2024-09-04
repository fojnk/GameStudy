package ru.nsu.GameStudy.authentication.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.authentication.dto.NotificationDTO;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.mappers.NotificationMapper;
import ru.nsu.GameStudy.mappers.UserMapper;
import ru.nsu.GameStudy.authentication.models.Notification;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.authentication.repositories.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public NotificationDTO getNotificationDTO(Long notificationId) {
        return notificationMapper.toDTO(getNotification(notificationId));
    }

    public List<NotificationDTO> getAllNotifications() {
        log.info("GET all notifications");
        return notificationRepository.findAll().stream().map(notificationMapper::toDTO).toList();
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        log.info("DELETE notification with id: {}", notificationId);
        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById(notificationId);
        } else {
            log.error("notification with id: {} not found", notificationId);
            throw new NotFoundException("notification not found");
        }
    }

    @Transactional
    public NotificationDTO updateNotification(Long notificationId, NotificationDTO request) {
        log.info("UPDATE notification with id: {}, " +
                        "title: {}, details: {}}", notificationId,
                request.getTitle(), request.getDetails());
        Notification notification = getNotification(notificationId);

        notificationMapper.updateNotificationFromDTO(notification, request);

        return save(notification);
    }

    @Transactional
    public NotificationDTO createNotification(NotificationDTO request) {
        log.info("CREATE notification with " +
                        "title: {}, details: {}}",
                request.getTitle(), request.getDetails());
        Notification notification = Notification.builder().build();
        notificationMapper.updateNotificationFromDTO(notification, request);

        return save(notification);
    }

    @Transactional
    public NotificationDTO sendNotificationToRecipient(Long id, Long userId) {
        log.info("ADD notification {} to user {}", id, userId);
        Notification notification = getNotification(id);
        User recipient = userService.getUser(userId);
        notification.getRecipients().add(recipient);
        recipient.getReceivedNotifications().add(notification);
        return save(notification);
    }

    @Transactional
    public NotificationDTO removeNotificationFromRecipient(Long id, Long userId) {
        log.info("REMOVE notification {} from user {}", id, userId);
        Notification notification = getNotification(id);
        User recipient = userService.getUser(userId);
        notification.getRecipients().remove(recipient);
        recipient.getReceivedNotifications().remove(notification);
        return save(notification);
    }

    @Transactional
    public UserDTO getNotificationSender(Long id) {
        log.info("GET sender of notification {}", id);
        Notification notification = getNotification(id);
        return userMapper.toDTO(notification.getSender());
    }

    @Transactional
    public List<NotificationDTO> getNotificationsBySenderAndRecipient(Long senderId, Long recipientId) {
        log.info("GET notifications by sender {} and recipient {}", senderId, recipientId);
        List<Notification> notifications = notificationRepository
                .findByRecipients_idAndSender_id(recipientId, senderId);
        if (notifications.isEmpty()) {
            log.error("notifications with sender {} and recipient {}", senderId, recipientId);
            throw new NotFoundException("notifications not found");
        }
        else {
            return notifications.stream().map(notificationMapper::toDTO).toList();
        }
    }

    @Transactional
    public Boolean confirmNotificationByRecipient(Long id, Long recipientId) {
        log.info("CONFIRM notification {} status for recipient {}", id, recipientId);
        Notification notification = getNotification(id);
        User recipient = userService.getUser(recipientId);
        if (notification.getRecipients().stream().allMatch(r -> r != recipient)) {
            log.error("notification {} hasn't been sent to user {}", id, recipientId);
            throw new IllegalCallerException();
        }
        if (notification.getRecipientsConfirmed().stream().anyMatch(r -> r == recipient)) {
            log.info("notification {} is already confirmed by user {}", id, recipientId);
            return false;
        }
        notification.getRecipientsConfirmed().add(recipient);
        return true;
    }

    protected NotificationDTO save(Notification notification) {
        return notificationMapper.toDTO(notificationRepository.save(notification));
    }

    protected Notification getNotification(Long id) {
        log.info("GET notification with id: {}", id);
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()) {
            log.error("notification with id: {} not found", id);
            throw new NotFoundException("notification not found");
        }
        else {
            return notification.get();
        }
    }
}
