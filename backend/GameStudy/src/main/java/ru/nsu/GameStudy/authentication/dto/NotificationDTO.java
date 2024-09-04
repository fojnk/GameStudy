package ru.nsu.GameStudy.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.util.List;

@Builder
@Getter
public class NotificationDTO {
        Long id;
        String title;
        String details;
        @JsonProperty(value = "recipient_ids")
        List<Long> recipientIds;
        @JsonProperty(value = "recipient_confirmed_ids")
        List<Long> recipientConfirmedIds;
        @JsonProperty(value = "sender_id")
        Long senderId;
}
