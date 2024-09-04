package ru.nsu.GameStudy.gamification.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.courses.services.TaskService;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.services.GameMethodService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления методами геймификации.")
public class GameMethodController {
    private final GameMethodService gameMethodService;
    private final TaskService taskService;

    @GetMapping("/game-methods")
    @Operation(description = "Получение списка всех методов геймификации.")
    public List<GameMethodDTO> getAllGameMethods() {
        return gameMethodService.getAllGameMethods();
    }

    @PostMapping("/game-methods")
    @Operation(description = "Создание нового метода геймификации.")
    public GameMethodDTO addGameMethod(@RequestBody GameMethodDTO request) throws ValueAlreadyExistsException {
        return gameMethodService.createGameMethod(request);
    }

    @GetMapping("/game-methods/{id}")
    @Operation(description = "Получение метода геймификации по id.")
    public GameMethodDTO getGameMethod(@PathVariable Long id) {
        return gameMethodService.getGameMethodDTO(id);
    }

    @PutMapping("/game-methods/{id}")
    @Operation(description = "Обновление метода геймификации по id.")
    public GameMethodDTO updateGameMethod(@PathVariable Long id, @RequestBody GameMethodDTO request) {
        return gameMethodService.updateGameMethod(id, request);
    }

    @DeleteMapping("/game-methods/{id}")
    @Operation(description = "Удаление метода геймификации по id.")
    public ResponseEntity<String> deleteGameMethod(@PathVariable Long id) {
        gameMethodService.deleteGameMethod(id);
        return ResponseEntity.ok().build();
    }

    //@GetMapping("/game-methods/{id}/tasks")
    //@Operation(description = "Получение списка задач по методу геймификации.")
    //public List<TaskDTO> getTasksForGameMethod(@PathVariable Long id) {
    //    return gameMethodService.getTasksByGameMethod(id);
    //}

    @GetMapping("/game-methods/{id}/tags")
    @Operation(description = "Получение списка тэгов по методу геймификации.")
    public List<GameTagDTO> getTagsForGameMethod(@PathVariable Long id) {
        return gameMethodService.getTagsInGameMethod(id);
    }

    @PutMapping("/game-methods/{id}/tags")
    @Operation(description = "Добавление тега в метод геймификации.")
    public ResponseEntity<String> addTagToGameMethod(
            @PathVariable Long id,
            @RequestBody List<Long> tagIds) {
        gameMethodService.addTagsToGameMethod(id, tagIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/game-methods/{id}/tags")
    @Operation(description = "Добавление тега в метод геймификации.")
    public ResponseEntity<String> removeTagFromGameMethod(
            @PathVariable Long id,
            @RequestBody List<Long> tagIds) {
        gameMethodService.removeTagsFromGameMethod(id, tagIds);
        return ResponseEntity.ok().build();
    }
}
