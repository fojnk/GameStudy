package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.services.CourseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Админка", description="Создание новых пользователей, разрешение доступа.")
public class AdminController {

    private final CourseService courseService;
    @GetMapping("/courses/")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> getAllCourses(@RequestParam Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }
}