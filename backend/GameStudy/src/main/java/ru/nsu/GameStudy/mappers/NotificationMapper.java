package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.authentication.dto.NotificationDTO;
import ru.nsu.GameStudy.authentication.models.Notification;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class NotificationMapper {
    @Mapping(target = "recipientIds", source = "notification.recipients")
    @Mapping(target = "recipientConfirmedIds", source = "notification.recipientsConfirmed")
    @Mapping(target = "senderId", source = "notification.sender.id")
    public abstract NotificationDTO toDTO(Notification notification);

    @Mapping(target = "recipients", source = "dto.recipientIds")
    @Mapping(target = "recipientsConfirmed", source = "dto.recipientConfirmedIds")
    @Mapping(target = "sender", source = "dto.senderId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateNotificationFromDTO(
            @MappingTarget Notification notification,
            NotificationDTO dto);
}
