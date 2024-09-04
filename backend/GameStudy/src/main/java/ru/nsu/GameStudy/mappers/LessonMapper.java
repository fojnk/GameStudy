package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.LessonRequest;
import ru.nsu.GameStudy.courses.models.Lesson;

@Mapper(componentModel = "spring", uses = {
        CourseMapper.class, ReferenceMapper.class, DisciplineMapper.class, MapUtils.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class LessonMapper {
    public abstract LessonDTO toDTO(Lesson lesson);

    @Mapping(target = "id", ignore = true)
    public abstract void updateLessonFromDTO(
            @MappingTarget Lesson lesson, LessonRequest dto);
}