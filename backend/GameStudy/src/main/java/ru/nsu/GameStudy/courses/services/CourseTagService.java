package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.mappers.CourseTagMapper;
import ru.nsu.GameStudy.course_attributes.repositories.CourseTagRepository;

import java.util.Optional;

@Service("CoursesCourseTagService")
@Slf4j
@RequiredArgsConstructor
class CourseTagService {
    private final CourseTagRepository tagRepository;
    private final CourseTagMapper tagMapper;

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

