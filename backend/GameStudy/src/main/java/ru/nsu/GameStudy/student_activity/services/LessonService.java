package ru.nsu.GameStudy.student_activity.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.mappers.LessonMapper;
import ru.nsu.GameStudy.courses.repositories.LessonRepository;

import java.util.Optional;

@Service("StudentActivityLessonService")
@Slf4j
@RequiredArgsConstructor
class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    protected LessonDTO save(Lesson lesson) {
        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }

    protected Lesson getLesson(Long id) {
        log.info("GET lesson with id: {}", id);
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isEmpty()) {
            log.error("lesson with id: {} not found", id);
            throw new NotFoundException("lesson not found");
        }
        else {
            return lesson.get();
        }
    }
}