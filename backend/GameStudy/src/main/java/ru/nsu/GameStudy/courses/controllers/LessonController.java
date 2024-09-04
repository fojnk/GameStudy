package ru.nsu.GameStudy.courses.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.courses.dto.LessonRequest;
import ru.nsu.GameStudy.courses.services.LessonService;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Уроки",
        description="Создание, обновление, удаление уроков.")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/")
    @Operation(description = "Получение списка всех уроков.")
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PostMapping("/")
    @Operation(description = "Создание нового урока.")
    public LessonDTO addLesson(@RequestBody LessonRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение урока по id.")
    public LessonDTO getLesson(@PathVariable Long id) {
        return lessonService.getLessonDTO(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление уроков по id.")
    public LessonDTO updateLesson(@PathVariable Long id, @RequestBody LessonRequest request) {
        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление урока по id.")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/tasks")
    @Operation(description = "Получение всех задач в уроке.")
    public List<TaskDTO> getTasksInLesson(@PathVariable Long id) {
        return lessonService.getTasksInLesson(id);
    }

    @PutMapping("/{id}/tasks")
    @Operation(description = "Добавление задачи в урок.")
    public ResponseEntity<String> addTaskToLesson(@PathVariable Long id, @RequestParam(name = "task-id") Long taskId) {
        lessonService.addTaskToLesson(id, taskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/tasks")
    @Operation(description = "Удаление задачи из урока.")
    public ResponseEntity<String> removeTaskFromLesson(@PathVariable Long id, @RequestParam(name = "task-id") Long taskId) {
        lessonService.removeTaskFromLesson(id, taskId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/game-methods")
    @Operation(description = "Получение списка всех методов геймификации в уроке.")
    public List<GameMethodDTO> getGameMethodsInLesson(@PathVariable Long id) {
        return lessonService.getLessonGameMethods(id);
    }

    @PutMapping("/{id}/game-methods")
    @Operation(description = "Добавление методов геймификации в урок.")
    public ResponseEntity<String> addGameMethodsToLesson(
            @PathVariable Long id,
            @RequestBody List<Long> methodIds) {
        lessonService.addGameMethodsToLesson(id, methodIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/game-methods")
    @Operation(description = "Удаление методов геймификации из урока.")
    public ResponseEntity<String> deleteGameMethodsFromLesson(
            @PathVariable Long id,
            @RequestBody List<Long> methodIds) {
        lessonService.removeGameMethodsFromLesson(id, methodIds);
        return ResponseEntity.ok().build();
    }
}
