package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.TeacherDTO;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Teacher;
import ru.nsu.GameStudy.repository.CourseRepository;
import ru.nsu.GameStudy.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CourseRepository courseRepository;

    public Teacher createTeacher(TeacherDTO request) {
        var user = userService.getUserByUserId(request.getUserId());
        var blog = blogService.createBlog(request.getBlog());

        Teacher teacher = Teacher.builder()
                .blog(blog)
                .userData(user)
                .organisation(request.getOrganisation())
                .birthDate(request.getBirthDate())
                .qualification(request.getQualification())
                .build();

        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("teacher not found"));
    }

    public Optional<List<Course>> getTeacherCourses(Long id) {
        return courseRepository.findByTeacherId(id);
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = getTeacher(id);

        teacherRepository.delete(teacher);
    }

    public Teacher updateTeacher(Long teacherId, TeacherDTO request) {
        Teacher teacher = getTeacher(teacherId);

        teacher.setQualification(request.getQualification());
        teacher.setOrganisation(request.getOrganisation());
        teacher.setBirthDate(request.getBirthDate());

        return teacherRepository.save(teacher);
    }
}