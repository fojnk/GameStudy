package ru.nsu.GameStudy.courses.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.dto.TaskRequest;
import ru.nsu.GameStudy.courses.services.TaskService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Задачи",
        description="Создание, обновление, удаление задач.")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    @Operation(description = "Получение списка всех задач.")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    @Operation(description = "Создание новой задачи.")
    public TaskDTO addTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение задачи по id.")
    public TaskDTO getTask(@PathVariable Long id) {
        return taskService.getTaskDTO(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление задачи по id.")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление задачи по id.")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
