package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.mappers.GameMethodMapper;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.gamification.repositories.GameMethodRepository;

import java.util.Optional;

@Service("CoursesGameMethodService")
@Slf4j
@RequiredArgsConstructor
public class GameMethodService {
    private final GameMethodRepository gameMethodRepository;
    private final GameMethodMapper gameMethodMapper;

    protected GameMethodDTO save(GameMethod method) {
        return gameMethodMapper.toDTO(gameMethodRepository.save(method));
    }

    protected GameMethod getGameMethod(Long id) {
        log.info("GET gameMethod with id: {}", id);
        Optional<GameMethod> gameMethod = gameMethodRepository.findById(id);
        if (gameMethod.isEmpty()) {
            log.error("gameMethod with id: {} not found", id);
            throw new NotFoundException("gameMethod not found");
        }
        else {
            return gameMethod.get();
        }
    }
}
