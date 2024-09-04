package ru.nsu.GameStudy.gamification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;

@Builder
@Getter
public class ScoreDTO {
        Long id;
        Integer experience;
        Time time;
        @JsonProperty(value = "student_id")
        Long studentId;
}