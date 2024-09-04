package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.student_activity.dto.LessonActivityDTO;
import ru.nsu.GameStudy.student_activity.models.LessonActivity;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class LessonActivityMapper {
    @Mapping(target = "lessonId", source = "activity.lesson.id")
    @Mapping(target = "studentId", source = "activity.student.id")
    public abstract LessonActivityDTO toDTO(LessonActivity activity);

    @Mapping(target = "lesson", source = "dto.lessonId")
    @Mapping(target = "student", source = "dto.studentId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateLessonActivityFromDTO(
            @MappingTarget LessonActivity activity,
            LessonActivityDTO dto);
}
