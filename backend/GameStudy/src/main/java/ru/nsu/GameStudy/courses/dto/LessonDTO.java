package ru.nsu.GameStudy.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;

import java.util.List;

@Builder
@Getter
public class LessonDTO {
        Long id;
        String title;
        String description;
        Integer experience;
        Integer complexity;
        @JsonProperty(value = "video_path")
        String videoPath;
        @JsonProperty(value = "image_path")
        String imagePath;
        DisciplineDTO discipline;
        @JsonProperty(value = "tasks")
        List<TaskDTO> tasks;
        @JsonProperty(value = "game_methods")
        List<GameMethodDTO> gameMethods;
}