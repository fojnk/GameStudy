package ru.nsu.GameStudy.student_activity.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.student_activity.dto.CourseProgressDTO;
import ru.nsu.GameStudy.student_activity.services.CourseProgressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-progress")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Общий прогресс по курсу.",
        description="Отслеживание прогресса прохождения курсов студентами.")
public class CourseProgressController {
    private final CourseProgressService progressService;

    @GetMapping("/")
    @Operation(description = "Получение списка всех прогрессов курсов.")
    public List<CourseProgressDTO> getAllCourseProgressByCourse(
            @RequestParam(name = "student-id", required = false) Long studentId,
            @RequestParam(name = "course-id", required = false) Long courseId) {
        if (studentId != null && courseId != null) {
            return List.of(progressService.getProgressByCourseAndStudent(courseId, studentId));
        }
        if (studentId != null) {
            return progressService.getAllProgressByStudent(studentId);
        }
        if (courseId != null) {
            return progressService.getAllProgressByCourse(courseId);
        }
        return progressService.getAllCourseProgress();
    }

    @PostMapping("/")
    @Operation(description = "Создание нового прогресса студента по курсу.")
    public CourseProgressDTO addCourseProgress(@RequestBody CourseProgressDTO request) {
        return progressService.createCourseProgress(request);
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение прогресса по курсу по id.")
    public CourseProgressDTO getCourseProgress(@PathVariable Long id) {
        return progressService.getCourseProgressDTO(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление прогресса по курсу по id.")
    public CourseProgressDTO updateCourseProgress(@PathVariable Long id,
                                              @RequestBody CourseProgressDTO request) {
        return progressService.updateCourseProgress(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление прогресса по курсу по id.")
    public ResponseEntity<String> deleteCourseProgress(@PathVariable Long id) {
        progressService.deleteCourseProgress(id);
        return ResponseEntity.ok().build();
    }
}

