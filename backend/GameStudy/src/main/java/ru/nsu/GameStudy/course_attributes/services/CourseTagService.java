package ru.nsu.GameStudy.course_attributes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.mappers.CourseTagMapper;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.course_attributes.repositories.CourseTagRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseTagService {
    private final CourseTagRepository tagRepository;
    private final CourseTagMapper tagMapper;

    public CourseTagDTO createTag(CourseTagDTO request) throws ValueAlreadyExistsException {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE tag request: {title: {}}", request.getTitle());
        CourseTag tag = CourseTag.builder().build();
        tagMapper.updateTagFromDTO(tag, request);

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
    public CourseTagDTO updateTag(Long tagId, CourseTagDTO request) {
        log.info("UPDATE tag with id: {}, request: {title: {}}", tagId, request.getTitle());
        CourseTag tag = getTag(tagId);

        tagMapper.updateTagFromDTO(tag, request);

        return save(tag);
    }

    public CourseTagDTO getTagDTO(Long tagId) {
        return tagMapper.toDTO(getTag(tagId));
    }

    public List<CourseTagDTO> getAllTags() {
        log.info("GET all tags");
        return tagRepository.findAll().stream().map(tagMapper::toDTO).toList();
    }

    protected CourseTagDTO save(CourseTag tag) {
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    protected CourseTag getTag(Long id) {
        log.info("GET tag with id: {}", id);
        Optional<CourseTag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            log.error("tag with id: {} not found", id);
            throw new NotFoundException("tag not found");
        }
        else {
            return tag.get();
        }
    }
}
