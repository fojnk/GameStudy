package ru.nsu.GameStudy.student_activity.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.student_activity.dto.LessonActivityDTO;
import ru.nsu.GameStudy.student_activity.services.LessonActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-tracking")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Активность.",
        description="Отслеживание активности прохождения уроков студентами.")
public class LessonActivityController {
    private final LessonActivityService activityService;

    @GetMapping("/lesson-activities")
    @Operation(description = "Получение списка всех результатов прохождения уроков.")
    public List<LessonActivityDTO> getAllLessonActivities(
            @RequestParam(name = "student-id", required = false) Long studentId,
            @RequestParam(name = "lesson-id", required = false) Long lessonId) {
        if (studentId != null && lessonId != null) {
            return List.of(activityService.getLessonActivityByLessonAndStudent(lessonId, studentId));
        }
        if (studentId != null) {
            return activityService.getActivityByStudent(studentId);
        }
        if (lessonId != null) {
            return activityService.getActivityByLesson(lessonId);
        }
        return activityService.getAllLessonActivity();
    }

    @PostMapping("/lesson-activities")
    @Operation(description = "Создание нового результата прохождения урока.")
    public LessonActivityDTO addLessonActivity(@RequestBody LessonActivityDTO request) {
        return activityService.createLessonActivity(request);
    }

    @GetMapping("/lesson-activities/{id}")
    @Operation(description = "Получение результата прохождения урока по id.")
    public LessonActivityDTO getLessonActivity(@PathVariable Long id) {
        return activityService.getLessonActivityDTO(id);
    }

    @PutMapping("/lesson-activities/{id}")
    @Operation(description = "Обновление результата прохождения урока по id.")
    public LessonActivityDTO updateLessonActivity(@PathVariable Long id,
                                              @RequestBody LessonActivityDTO request) {
        return activityService.updateLessonActivity(id, request);
    }

    @DeleteMapping("/lesson-activities/{id}")
    @Operation(description = "Удаление результата прохождения урока по id.")
    public ResponseEntity<String> deleteLessonActivity(@PathVariable Long id) {
        activityService.deleteLessonActivity(id);
        return ResponseEntity.ok().build();
    }
}

