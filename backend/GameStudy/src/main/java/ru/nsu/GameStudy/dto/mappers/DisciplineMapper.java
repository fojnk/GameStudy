package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.DisciplineDTO;
import ru.nsu.GameStudy.models.Discipline;

public class DisciplineMapper {
    public static DisciplineDTO toDTO(Discipline discipline) {
        return new DisciplineDTO(
                discipline.getId(),
                discipline.getTitle()
        );
    }
}