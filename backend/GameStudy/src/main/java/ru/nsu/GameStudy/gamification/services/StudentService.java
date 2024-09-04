package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.mappers.BlogMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service("GamificationStudentService")
@Slf4j
@RequiredArgsConstructor
class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    protected StudentDTO save(Student student) {
        return studentMapper.toDTO(studentRepository.save(student));
    }

    protected Student getStudent(Long id) {
        log.info("GET student with id: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            log.error("student with id: {} not found", id);
            throw new NotFoundException("student not found");
        }
        else {
            return student.get();
        }
    }
}


