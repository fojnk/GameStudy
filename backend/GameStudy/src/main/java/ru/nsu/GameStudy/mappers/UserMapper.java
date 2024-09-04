package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.authentication.models.User;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class UserMapper {
    @Mapping(target = "receivedNotificationIds", source = "user.receivedNotifications")
    @Mapping(target = "confirmedNotificationIds", source = "user.confirmedNotifications")
    @Mapping(target = "sentNotificationIds", source = "user.sentNotifications")
    public abstract UserDTO toDTO(User user);

    @Mapping(target = "receivedNotifications", source = "dto.receivedNotificationIds")
    @Mapping(target = "confirmedNotifications", source = "dto.confirmedNotificationIds")
    @Mapping(target = "sentNotifications", source = "dto.sentNotificationIds")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract void updateUserFromDTO(
            @MappingTarget User user,
            UserDTO dto);
}