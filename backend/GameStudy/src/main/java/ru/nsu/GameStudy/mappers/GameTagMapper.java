package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.models.GameTag;

@Mapper(componentModel = "spring", uses = {
        CourseMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class GameTagMapper {
    public abstract GameTagDTO toDTO(GameTag tag);

    @Mapping(target = "id", ignore = true)
    public abstract void updateFromDTO(
            @MappingTarget GameTag tag,
            GameTagDTO dto);
}
