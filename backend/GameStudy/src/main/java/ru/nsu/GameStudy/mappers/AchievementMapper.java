package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.gamification.dto.AchievementDTO;
import ru.nsu.GameStudy.gamification.models.Achievement;

@Mapper(componentModel = "spring", uses = {
        StudentMapper.class, ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class AchievementMapper {
    @Mapping(target = "studentIds", source = "achievement.students")
    public abstract AchievementDTO toDTO(Achievement achievement);

    @Mapping(target = "students", source = "achievementDTO.studentIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateAchievementFromDTO(
            @MappingTarget Achievement achievement,
            AchievementDTO achievementDTO);
}