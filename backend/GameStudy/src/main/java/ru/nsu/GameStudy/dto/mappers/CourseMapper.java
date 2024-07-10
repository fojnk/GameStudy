package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.CourseDTO;
import ru.nsu.GameStudy.models.Course;

public class CourseMapper {
    public static CourseDTO toDto(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCreator().getId(),
                course.getCost(),
                course.getRating(),
                course.getAmtPassedUsers()
        );
    }
}