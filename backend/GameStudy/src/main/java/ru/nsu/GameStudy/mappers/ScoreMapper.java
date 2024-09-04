package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.gamification.dto.ScoreDTO;
import ru.nsu.GameStudy.gamification.models.Score;

@Mapper(componentModel = "spring", uses = {
        StudentMapper.class, ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ScoreMapper {
    @Mapping(target = "studentId", source = "score.student.id")
    public abstract ScoreDTO toDTO(Score score);

    @Mapping(target = "student", source = "dto.studentId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateScoreFromDTO(
            @MappingTarget Score score,
            ScoreDTO dto);
}
