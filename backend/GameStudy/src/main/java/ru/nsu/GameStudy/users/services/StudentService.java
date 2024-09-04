package ru.nsu.GameStudy.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.mappers.BlogMapper;
import ru.nsu.GameStudy.mappers.CourseMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentRequest;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final BlogService blogService;
    private final BlogMapper blogMapper;
    private final CourseMapper courseMapper;

    @Transactional
    public StudentDTO createStudent(StudentRequest request)
            throws ValueAlreadyExistsException {
        if (studentRepository.existsByUserData_id(request.getUser())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE student request: {activity: {}, birthDate: {}, " +
                        "experience: {}, userData: {}, wallet: {}}", request.getActivity(),
                request.getBirthDate(), request.getExperience(), request.getUser(), request.getWallet());

        BlogDTO blogDTO = BlogDTO.builder().build();
        blogDTO = blogService.createBlog(blogDTO);
        Student student = Student.builder()
                .experience(1)
                .blog(blogService.getBlog(blogDTO.getId()))
                .build();
        studentMapper.updateStudentFromDTO(student, request);

        return save(student);
    }

    public List<StudentDTO> getAllStudents() {
        log.info("GET all students");
        return studentRepository.findAll().stream().map(studentMapper::toDTO).toList();
    }

    public StudentDTO getStudentByUser(Long userId) {
        log.info("GET student by user id: {}", userId);
        Optional<Student> student = studentRepository.findByUserData_id(userId);
        if (student.isEmpty()) {
            log.error("no student with user id {} found", userId);
            throw new NotFoundException("no student found");
        }
        return studentMapper.toDTO(student.get());
    }

    public StudentDTO getStudentDTO(Long id) {
        return studentMapper.toDTO(getStudent(id));
    }

    @Transactional
    public void deleteStudentById(Long studentId) {
        log.info("DELETE student with id: {}", studentId);
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        } else {
            log.error("student with id: {} not found", studentId);
            throw new NotFoundException("student not found");
        }
    }

    @Transactional
    public StudentDTO updateStudent(Long studentId, StudentRequest request) {
        log.info("UPDATE student with id: {},  request: {activity: {}, birthDate: {}, " +
                        "experience: {}, userData: {}, wallet: {}}", studentId, request.getActivity(),
                request.getBirthDate(), request.getExperience(), request.getUser(), request.getWallet());
        Student student = getStudent(studentId);

        studentMapper.updateStudentFromDTO(student, request);

        return save(student);
    }

    @Transactional
    public BlogDTO getStudentBlog(Long studentId) {
        log.info("GET student {} blog", studentId);
        Student student = getStudent(studentId);
        return blogMapper.toDTO(student.getBlog());
    }

    @Transactional
    public List<CourseDTO> getCoursesForStudent(Long studentId) {
        log.info("GET all courses for student {}", studentId);
        Student student = getStudent(studentId);
        return student
                .getCourses()
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

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

