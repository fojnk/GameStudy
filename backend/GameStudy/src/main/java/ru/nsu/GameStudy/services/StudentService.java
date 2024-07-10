package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.BlogDTO;
import ru.nsu.GameStudy.dto.StudentDTO;
import ru.nsu.GameStudy.models.Blog;
import ru.nsu.GameStudy.models.Student;
import ru.nsu.GameStudy.repository.StudentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;


    public Student createStudent(StudentDTO request) {
        var user = userService.getUserByUserId(request.getUser_id());
        var blog = blogService.createBlog(request.getBlog());

        Student student = Student.builder()
                .blog(blog)
                .activity(request.getActivity())
                .birthDate(request.getBirthDate())
                .experience(request.getExperience())
                .userData(user)
                .wallet(request.getWallet())
                .build();

        return studentRepository.save(student);
    }

    public Student getStudentByStudentId(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("student not found"));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserData_id(userId)
                .orElseThrow(() -> new NotFoundException("student not found"));
    }

    public void deleteStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("student not found"));

        studentRepository.delete(student);
    }

    public Student updateStudent(Long studentId, StudentDTO request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("student not found"));

        student.setActivity(request.getActivity());
        student.setWallet(request.getWallet());
        student.setBirthDate(request.getBirthDate());
        student.setExperience(request.getExperience());

        return studentRepository.save(student);
    }
}
