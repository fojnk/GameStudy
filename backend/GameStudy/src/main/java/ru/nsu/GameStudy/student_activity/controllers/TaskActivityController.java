package ru.nsu.GameStudy.student_activity.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.student_activity.dto.TaskActivityDTO;
import ru.nsu.GameStudy.student_activity.services.TaskActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-tracking")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Активность.",
        description="Отслеживание активности выполнения задач студентами.")
public class TaskActivityController {
    private final TaskActivityService activityService;

    @GetMapping("/task-activities")
    @Operation(description = "Получение списка всех результатов выполнения задач.")
    public List<TaskActivityDTO> getAllTaskActivities(
            @RequestParam(name = "student-id", required = false) Long studentId,
            @RequestParam(name = "task-id", required = false) Long taskId) {
        if (studentId != null && taskId != null) {
            return List.of(activityService.getTaskActivityByTaskAndStudent(taskId, studentId));
        }
        if (studentId != null) {
            return activityService.getTaskActivitiesByStudent(studentId);
        }
        if (taskId != null) {
            return activityService.getTaskActivitiesByTask(taskId);
        }
        return activityService.getAllTasksActivities();
    }

    @PostMapping("/task-activities")
    @Operation(description = "Создание нового результата выполнения задачи.")
    public TaskActivityDTO addTaskActivity(@RequestBody TaskActivityDTO request) {
        return activityService.createTaskActivity(request);
    }

    @GetMapping("/task-activities/{id}")
    @Operation(description = "Получение результата выполнения задачи по id.")
    public TaskActivityDTO getTaskActivity(@PathVariable Long id) {
        return activityService.getTaskActivityDTO(id);
    }

    @PutMapping("/task-activities/{id}")
    @Operation(description = "Обновление результата выполнения задачи по id.")
    public TaskActivityDTO updateTaskActivity(@PathVariable Long id,
                                              @RequestBody TaskActivityDTO request) {
        return activityService.updateTaskActivity(id, request);
    }

    @DeleteMapping("/task-activities/{id}")
    @Operation(description = "Удаление результата выполнения задачи по id.")
    public ResponseEntity<String> deleteTaskActivity(@PathVariable Long id) {
        activityService.deleteTaskActivity(id);
        return ResponseEntity.ok().build();
    }
}

