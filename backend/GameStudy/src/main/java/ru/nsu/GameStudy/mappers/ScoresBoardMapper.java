package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.gamification.dto.ScoresBoardDTO;
import ru.nsu.GameStudy.gamification.models.ScoresBoard;

@Mapper(componentModel = "spring", uses = {
        ScoreMapper.class, ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ScoresBoardMapper {
    @Mapping(target = "scoreIds", source = "board.scores")
    @Mapping(target = "courseId", source = "board.course.id")
    public abstract ScoresBoardDTO toDTO(ScoresBoard board);

    @Mapping(target = "scores", source = "dto.scoreIds")
    @Mapping(target = "course", source = "dto.courseId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateBoardFromDTO(
            @MappingTarget ScoresBoard board,
            ScoresBoardDTO dto);
}
