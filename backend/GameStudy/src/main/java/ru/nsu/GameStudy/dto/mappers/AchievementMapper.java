package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.AchievementDTO;
import ru.nsu.GameStudy.models.Achievements;

public class AchievementMapper {
    public static AchievementDTO toDTO(Achievements achievements) {
        return new AchievementDTO(
                achievements.getId(),
                achievements.getTitle(),
                achievements.getRequiredExp()
        );
    }
}