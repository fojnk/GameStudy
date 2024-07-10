package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.LessonDTO;
import ru.nsu.GameStudy.models.Lesson;

public class LessonMapper {
    public static LessonDTO toDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getDiscipline().getId(),
                lesson.getExperience(),
                lesson.getComplexity()
        );
    }
}