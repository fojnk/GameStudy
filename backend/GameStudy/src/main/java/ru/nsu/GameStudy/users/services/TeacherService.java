package ru.nsu.GameStudy.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.mappers.CourseMapper;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.mappers.BlogMapper;
import ru.nsu.GameStudy.mappers.TeacherMapper;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.users.dto.TeacherRequest;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.models.Teacher;
import ru.nsu.GameStudy.users.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final BlogService blogService;
    private final BlogMapper blogMapper;
    private final CourseMapper courseMapper;

    @Transactional
    public TeacherDTO createTeacher(TeacherRequest request) throws ValueAlreadyExistsException {
        if (teacherRepository.existsByUserData_id(request.getId())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE teacher, request : {blog: {}, userId: {}, " +
                        "organisation: {}, birthDate: {}, qualification: {}}", request.getBlog(),
                request.getUser(), request.getOrganisation(), request.getBirthDate(), request.getQualification());

        BlogDTO blogDTO = BlogDTO.builder().build();
        blogDTO = blogService.createBlog(blogDTO);
        Teacher teacher = Teacher.builder()
                .blog(blogService.getBlog(blogDTO.getId()))
                .build();
        save(teacher);
        teacherMapper.updateTeacherFromDTO(teacher, request);


        return teacherMapper.toDTO(teacher);
    }

    public List<TeacherDTO> getAllTeachers() {
        log.info("GET all teachers");
        return teacherRepository.findAll().stream().map(teacherMapper::toDTO).toList();
    }

    public TeacherDTO getTeacherByUser(Long userId) {
        log.info("GET teacher by user id: {}", userId);
        Optional<Teacher> teacher = teacherRepository.findByUserData_id(userId);
        if (teacher.isEmpty()) {
            log.error("no teacher with user id {} found", userId);
            throw new NotFoundException("no teacher found");
        }
        return teacherMapper.toDTO(teacher.get());
    }

    public TeacherDTO getTeacherDTO(Long id) {
        return teacherMapper.toDTO(getTeacher(id));
    }

    @Transactional
    public void deleteTeacher(Long id) {
        log.info("DELETE teacher with id: {}", id);
        if  (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            log.error("teacher with id: {} not found", id);
            throw new NotFoundException("teacher not found");
        }
    }

    @Transactional
    public TeacherDTO updateTeacher(Long teacherId, TeacherRequest request) {
        log.info("UPDATE teacher with id: {}, request : {blog: {}, userId: {}, " +
                        "organisation: {}, birthDate: {}, qualification: {}}", teacherId, request.getBlog(),
                request.getUser(), request.getOrganisation(), request.getBirthDate(), request.getQualification());
        Teacher teacher = getTeacher(teacherId);
        teacherMapper.updateTeacherFromDTO(teacher, request);

        return save(teacher);
    }

    @Transactional
    public BlogDTO getTeacherBlog(Long teacherId) {
        log.info("GET teacher {} blog", teacherId);
        Teacher teacher = getTeacher(teacherId);
        return blogMapper.toDTO(teacher.getBlog());
    }

    @Transactional
    public boolean confirmTeacherStatus(Long id) {
        log.info("CONFIRM status of teacher {}", id);
        Teacher teacher = getTeacher(id);
        if (teacher.getStatusConfirmed()) {
            log.info("status of teacher {} already confirmed", id);
            return false;
        }
        teacher.setStatusConfirmed(true);
        return true;
    }

    @Transactional
    public List<CourseDTO> getCoursesForTeacher(Long teacherId) {
        log.info("GET all courses for teacher {}", teacherId);
        Teacher teacher = getTeacher(teacherId);
        return teacher
                .getCourses()
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

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