package ru.nsu.GameStudy.course_attributes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.course_attributes.repositories.GameTagRepository;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.mappers.GameTagMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameTagService {
    private final GameTagRepository tagRepository;
    private final GameTagMapper tagMapper;

    public GameTagDTO createTag(GameTagDTO request) throws ValueAlreadyExistsException {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE tag request: {title: {}}", request.getTitle());
        GameTag tag = GameTag.builder().build();
        tagMapper.updateFromDTO(tag, request);

        return save(tag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        log.info("DELETE tag with id: {}", tagId);
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
        } else {
            log.error("tag with id: {} not found", tagId);
            throw new NotFoundException("tag not found");
        }
    }

    @Transactional
    public GameTagDTO updateTag(Long tagId, GameTagDTO request) {
        log.info("UPDATE tag with id: {}, request: {title: {}}", tagId, request.getTitle());
        GameTag tag = getTag(tagId);

        tagMapper.updateFromDTO(tag, request);

        return save(tag);
    }

    public GameTagDTO getTagDTO(Long tagId) {
        return tagMapper.toDTO(getTag(tagId));
    }

    public List<GameTagDTO> getAllTags() {
        log.info("GET all tags");
        return tagRepository.findAll().stream().map(tagMapper::toDTO).toList();
    }

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
