package ru.nsu.GameStudy.gamification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AchievementDTO {
        Long id;
        String title;
        @JsonProperty(value = "image_path")
        String imagePath;
        @JsonProperty(value = "required_exp")
        Integer requiredExp;
        @JsonProperty(value = "student_ids")
        List<Long> studentIds;
}