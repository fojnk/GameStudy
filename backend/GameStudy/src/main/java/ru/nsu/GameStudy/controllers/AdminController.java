package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Админка", description="Создание новых пользователей, разрешение доступа.")
public class AdminController {

    private final CourseService courseService;
    @GetMapping("/courses/")
    @Operation(description = "Получение списка всех курсов.")
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{courseId}")
    @Operation(description = "Получение курса по id.")
    public CourseDTO getCourse(@RequestParam Long courseId) {
        return courseService.getCourseDTO(courseId);
    }
}