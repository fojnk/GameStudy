package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;

@Mapper(componentModel = "spring", uses = {
        CourseMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CourseTagMapper {
    public abstract CourseTagDTO toDTO(CourseTag tag);

    @Mapping(target = "id", ignore = true)
    public abstract void updateTagFromDTO(
            @MappingTarget CourseTag tag,
            CourseTagDTO dto);
}