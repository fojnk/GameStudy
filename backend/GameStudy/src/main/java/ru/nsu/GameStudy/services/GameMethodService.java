package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.GameMethodDTO;
import ru.nsu.GameStudy.models.GameMethod;
import ru.nsu.GameStudy.repository.GameMethodRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GameMethodService {
    private final GameMethodRepository gameMethodRepository;

    public GameMethod getGameMethod(Integer id) {
        return gameMethodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("gameMethod not found"));
    }

    public List<GameMethod> getAllGameMethods() {
        return gameMethodRepository.findAll();
    }

    public void deleteGameMethod(Integer id) {
        var gameMethod = getGameMethod(id);

        gameMethodRepository.delete(gameMethod);
    }

    public GameMethod updateGameMethod(Integer id, GameMethodDTO request) {
        var gameMethod = getGameMethod(id);

        gameMethod.setType(request.getType());
        gameMethod.setTitle(request.getTitle());

        return gameMethodRepository.save(gameMethod);
    }

    public GameMethod createGameMethod(GameMethodDTO request) {
        GameMethod gameMethod = GameMethod.builder()
                .type(request.getType())
                .title(request.getTitle())
                .build();

        return gameMethodRepository.save(gameMethod);
    }
}