package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.dto.BlogDTO;
import ru.nsu.GameStudy.dto.CourseDTO;
import ru.nsu.GameStudy.dto.StudentDTO;
import ru.nsu.GameStudy.dto.mappers.BlogMapper;
import ru.nsu.GameStudy.dto.mappers.CourseMapper;
import ru.nsu.GameStudy.dto.mappers.StudentMapper;
import ru.nsu.GameStudy.models.Blog;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Student;
import ru.nsu.GameStudy.response.ApiResponse;
import ru.nsu.GameStudy.services.BlogService;
import ru.nsu.GameStudy.services.CourseService;
import ru.nsu.GameStudy.services.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Студенты", description="Общие эндпоинты для работы со студентами.")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        try {
            var student = studentService.getStudentByUserId(id);
            return ResponseEntity.ok(StudentMapper.toDto(student));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try {
            var students = studentService.getAllStudents().stream().map(StudentMapper::toDto).toList();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addStudent(@RequestBody StudentDTO request) {
        try {
            studentService.createStudent(request);
            return ResponseEntity.ok(new ApiResponse(true, "student created"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO request) {
        try {
            updateStudent(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "student updated"));
        }catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudentById(id);
            return ResponseEntity.ok(new ApiResponse(true, "student deleted"));
        }catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @GetMapping("/{id}/blog/")
    public ResponseEntity<BlogDTO> getStudentBlog(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentByStudentId(id);
            return ResponseEntity.ok(BlogMapper.toDTO(student.getBlog()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/courses/")
    public ResponseEntity<List<CourseDTO>> getStudentCourses(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentByStudentId(id);
            var courses = courseService.getStudentCourses(id).get();
            return ResponseEntity.ok(courses.stream().map(CourseMapper::toDto).toList());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 }
