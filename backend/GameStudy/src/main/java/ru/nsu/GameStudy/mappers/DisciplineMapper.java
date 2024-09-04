package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.course_attributes.models.Discipline;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class DisciplineMapper {
    public abstract DisciplineDTO toDTO(Discipline discipline);

    @Mapping(target = "id", ignore = true)
    public abstract void updateDisciplineFromDTO(
            @MappingTarget Discipline discipline, DisciplineDTO dto);
}