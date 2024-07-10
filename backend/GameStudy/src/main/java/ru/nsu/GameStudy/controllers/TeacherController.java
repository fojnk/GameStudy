package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.dto.TeacherDTO;
import ru.nsu.GameStudy.dto.mappers.TeacherMapper;
import ru.nsu.GameStudy.models.Blog;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.response.ApiResponse;
import ru.nsu.GameStudy.services.BlogService;
import ru.nsu.GameStudy.services.CourseService;
import ru.nsu.GameStudy.services.TeacherService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Учителя", description="Общие эндпоинты для работы с учителями.")
public class TeacherController {
    @Autowired
    private final TeacherService teacherService;

    @Autowired
    private final BlogService blogService;

    @Autowired
    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        try {
            var teacher = teacherService.getTeacher(id);

            return ResponseEntity.ok(TeacherMapper.toDTO(teacher));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok(new ApiResponse(true, "teacher deleted"));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(false, e.toString()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        var teachers = teacherService.getAllTeachers().stream().map(TeacherMapper::toDTO).toList();
        return ResponseEntity.ok(teachers);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addTeacher(@RequestBody TeacherDTO request) {
        try {
            teacherService.createTeacher(request);
            return ResponseEntity.ok(new ApiResponse(true, "teacher created"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherDTO request) {
        try {
            teacherService.updateTeacher(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "teacher updated"));
        }catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @GetMapping("/{id}/blog/")
    public ResponseEntity<Blog> getTeacherBlog(@PathVariable Long id) {
        try {
            var teacher = teacherService.getTeacher(id);
            return ResponseEntity.ok(blogService.getBlog(teacher.getBlog().getId()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/courses/")
    public ResponseEntity<Optional<List<Course>>> getTeacherCourses(@PathVariable Long id) {
        try {
            var teacher = teacherService.getTeacher(id);
            return ResponseEntity.ok(teacherService.getTeacherCourses(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}