package ru.nsu.GameStudy.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.authentication.models.Notification;
import ru.nsu.GameStudy.authentication.models.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findBySender_id(Long senderId);

    List<Notification> findByRecipients_idAndSender_id(
            Long recipientId, Long senderId);
}
