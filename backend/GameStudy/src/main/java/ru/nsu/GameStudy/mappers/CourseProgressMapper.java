package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.student_activity.dto.CourseProgressDTO;
import ru.nsu.GameStudy.student_activity.models.CourseProgress;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CourseProgressMapper {
    @Mapping(target = "courseId", source = "progress.course.id")
    @Mapping(target = "studentId", source = "progress.student.id")
    @Mapping(target = "passedTaskIds", source = "progress.passedTasks")
    @Mapping(target = "passedLessonIds", source = "progress.passedLessons")
    public abstract CourseProgressDTO toDTO(CourseProgress progress);

    @Mapping(target = "course", source = "progressDTO.courseId")
    @Mapping(target = "student", source = "progressDTO.studentId")
    @Mapping(target = "passedTasks", source = "progressDTO.passedTaskIds")
    @Mapping(target = "passedLessons", source = "progressDTO.passedLessonIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateCourseProgressFromDTO(
            @MappingTarget CourseProgress progress, CourseProgressDTO progressDTO);
}
