package ru.nsu.GameStudy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.GameStudy.models.User;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    @JsonProperty(value = "creator_uid")
    private Long creatorUId;
    private BigDecimal cost;
    private BigDecimal rating;
    @JsonProperty(value = "amt_passed_users")
    private Integer amtPassedUsers;
}