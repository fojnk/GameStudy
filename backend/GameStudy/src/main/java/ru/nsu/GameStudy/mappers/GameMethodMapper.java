package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.models.GameMethod;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class GameMethodMapper {
    @Mapping(target = "tagIds", source = "gameMethod.tags")
    public abstract GameMethodDTO toDTO(GameMethod gameMethod);

    @Mapping(target = "tags", source = "dto.tagIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateGameMethodFromDTO(
            @MappingTarget GameMethod method, GameMethodDTO dto);
}