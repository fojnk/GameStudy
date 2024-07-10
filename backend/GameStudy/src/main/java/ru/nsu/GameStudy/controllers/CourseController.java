package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.dto.CourseDTO;
import ru.nsu.GameStudy.dto.mappers.CourseMapper;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Lesson;
import ru.nsu.GameStudy.response.ApiResponse;
import ru.nsu.GameStudy.services.CourseService;
import ru.nsu.GameStudy.services.LessonService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Курсы", description="Общие эндпоинты для работы с курсами.")
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        var courses = courseService.getAllCourses().stream().map(CourseMapper::toDto).toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseDTO request) {
        try {
            courseService.createCourse(request);
            return ResponseEntity.ok(new ApiResponse(true, "course added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.toString()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Long id
            , @RequestBody CourseDTO request) {
        try {
            courseService.updateCourse(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "course updated"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(CourseMapper.toDto(courseService.getCourse(id)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse(true, "course deleted"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, e.toString()));
        }
    }

    @GetMapping("/{id}/lessons/")
    public ResponseEntity<List<Lesson>> getCourseLessons(@PathVariable Long id) {
        var lessons = lessonService.getLessonsInCourse(id);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<?> getCourseLessonById(@PathVariable Long courseId,
                                                 @PathVariable Long lessonId) {
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}