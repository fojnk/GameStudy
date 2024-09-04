package ru.nsu.GameStudy.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.authentication.models.Role;

import java.util.List;

@Builder
@Getter
public class UserDTO {
       Long id;
       String name;
       String surname;
       @JsonProperty(value = "fathers_name")
       String fathersName;
       @JsonProperty(value = "phone_number")
       String phoneNumber;
       @JsonProperty(value = "email")
       String email;
       @JsonProperty(value = "password")
       String password;
       Role role;
       @JsonProperty(value = "received_notification_ids")
       List<Long> receivedNotificationIds;
       @JsonProperty(value = "confirmed_notification_ids")
       List<Long> confirmedNotificationIds;
       @JsonProperty(value = "sent_notification_ids")
       List<Long> sentNotificationIds;
}
