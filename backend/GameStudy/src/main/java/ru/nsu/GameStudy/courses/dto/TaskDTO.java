package ru.nsu.GameStudy.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;

import java.sql.Time;
import java.util.List;

@Builder
@Getter
public class TaskDTO {
      Long id;
      String title;
      String description;
      Time time;
      Integer experience;
      Integer complexity;
      @JsonProperty(value = "video_path")
      String videoPath;
      String answer;
      DisciplineDTO discipline;
      @JsonProperty(value = "game_methods")
      List<GameMethodDTO> gameMethods;
}