package ru.nsu.GameStudy.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.courses.repositories.CourseRepository;

import ru.nsu.GameStudy.mappers.CourseMapper;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.models.Student;

import java.util.Optional;

@Service("UsersCourseService")
@Slf4j
@RequiredArgsConstructor
class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    protected CourseDTO save(Course course) {
        return courseMapper.toDTO(courseRepository.save(course));
    }

    protected Course getCourse(Long id) {
        log.info("GET course with id: {}", id);
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            log.error("course with id: {} not found", id);
            throw new NotFoundException("course not found");
        }
        else {
            return course.get();
        }
    }
}

