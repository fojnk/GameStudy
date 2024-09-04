package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.mappers.GameMethodMapper;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.gamification.repositories.GameMethodRepository;
import ru.nsu.GameStudy.mappers.GameTagMapper;
import ru.nsu.GameStudy.mappers.LessonMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameMethodService {
    private final GameMethodRepository gameMethodRepository;
    private final GameMethodMapper gameMethodMapper;
    private final GameTagService tagService;
    private final GameTagMapper tagMapper;
    private final LessonMapper lessonMapper;

    public GameMethodDTO getGameMethodDTO(Long id) {
        return gameMethodMapper.toDTO(getGameMethod(id));
    }

    public List<GameMethodDTO> getAllGameMethods() {
        log.info("GET all game methods");
        return gameMethodRepository.findAll().stream().map(gameMethodMapper::toDTO).toList();
    }

    @Transactional
    public void deleteGameMethod(Long id) {
        log.info("DELETE gameMethod with id: {}", id);
        if (gameMethodRepository.existsById(id)) {
            gameMethodRepository.deleteById(id);
        } else {
            log.error("gameMethod with id: {} not found", id);
            throw new NotFoundException("game method not found");
        }
    }

    @Transactional
    public GameMethodDTO updateGameMethod(Long id, GameMethodDTO request) {
        log.info("UPDATE gameMethod with id: {}, " +
                "request: {type: {}, title: {}}", id, request.getType(), request.getTitle());
        var gameMethod = getGameMethod(id);
        gameMethodMapper.updateGameMethodFromDTO(gameMethod, request);

        return save(gameMethod);
    }

    public GameMethodDTO createGameMethod(GameMethodDTO request)
            throws ValueAlreadyExistsException {
        if (gameMethodRepository.existsByTitle(request.getTitle())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE gameMethod request: {type: {}, title: {}}", request.getType(), request.getTitle());
        GameMethod gameMethod = GameMethod.builder().build();
        gameMethodMapper.updateGameMethodFromDTO(gameMethod, request);

        return save(gameMethod);
    }

    public List<GameMethodDTO> getGameMethodsByTag(Long tagId) {
        log.info("GET game methods by tag {}", tagId);
        return gameMethodRepository
                .findByTags_id(tagId)
                .stream()
                .map(gameMethodMapper::toDTO)
                .toList();
    }

    public List<GameTagDTO> getTagsInGameMethod(Long methodId) {
        log.info("GET game method's tags, method: {}", methodId);
        GameMethod method = getGameMethod(methodId);
        return method.getTags().stream().map(tagMapper::toDTO).toList();
    }

    public GameMethodDTO addTagsToGameMethod(Long methodId, List<Long> tagIds) {
        log.info("ADD tag {} to game method: {}", tagIds, methodId);
        GameMethod method = getGameMethod(methodId);
        List<Long> exceptIds = method.getTags().stream().map(GameTag::getId).toList();
        List<Long> ids = tagIds
                .stream()
                .filter(x ->
                        !exceptIds.contains(x))
                .toList();
        method
                .getTags()
                .addAll(ids.stream().map(tagService::getTag).toList());
        return save(method);
    }

    public GameMethodDTO removeTagsFromGameMethod(Long methodId, List<Long> tagIds) {
        log.info("REMOVE tag {} from game method: {}", tagIds, methodId);
        GameMethod method = getGameMethod(methodId);
        method
                .getTags()
                .removeAll(tagIds.stream().map(tagService::getTag).toList());
        return save(method);
    }

    public List<LessonDTO> getLessonsWithGameMethod(Long methodId) {
        log.info("GET lessons with gameMethod {}", methodId);
        GameMethod method = getGameMethod(methodId);
        return method
                .getLessons()
                .stream()
                .map(lessonMapper::toDTO)
                .toList();
    }

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