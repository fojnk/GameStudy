package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.GameMethodDTO;
import ru.nsu.GameStudy.models.GameMethod;

public class GameMethodMapper {
    public static GameMethodDTO toDTO(GameMethod gameMethod) {
        return new GameMethodDTO(
                gameMethod.getId(),
                gameMethod.getTitle(),
                gameMethod.getType()
        );
    }
}