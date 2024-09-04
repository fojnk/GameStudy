package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.course_attributes.repositories.GameTagRepository;
import ru.nsu.GameStudy.mappers.GameTagMapper;

import java.util.Optional;

@Service("GamificationGameTagService")
@Slf4j
@RequiredArgsConstructor
class GameTagService {
    private final GameTagRepository tagRepository;
    private final GameTagMapper tagMapper;

    protected GameTagDTO save(GameTag tag) {
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    protected GameTag getTag(Long id) {
        log.info("GET tag with id: {}", id);
        Optional<GameTag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            log.error("tag with id: {} not found", id);
            throw new NotFoundException("tag not found");
        }
        else {
            return tag.get();
        }
    }
}

