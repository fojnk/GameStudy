package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.mappers.TeacherMapper;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.users.models.Teacher;
import ru.nsu.GameStudy.users.repositories.TeacherRepository;

import java.util.Optional;

@Service("CoursesTeacherService")
@Slf4j
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    protected TeacherDTO save(Teacher teacher) {
        return teacherMapper.toDTO(teacherRepository.save(teacher));
    }

    protected Teacher getTeacher(Long id) {
        log.info("GET teacher with id {}", id);
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            log.error("teacher with id: {} not found", id);
            throw new NotFoundException("teacher not found");
        }
        else {
            return teacher.get();
        }
    }
}