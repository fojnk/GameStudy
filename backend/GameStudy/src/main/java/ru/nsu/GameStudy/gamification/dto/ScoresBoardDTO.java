package ru.nsu.GameStudy.gamification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ScoresBoardDTO {
    Long id;
    String title;
    @JsonProperty(value = "score_ids")
    List<Long> scoreIds;
    @JsonProperty(value = "course_id")
    Long courseId;
}
