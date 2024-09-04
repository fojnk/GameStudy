package ru.nsu.GameStudy.gamification.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.dto.AchievementDTO;
import ru.nsu.GameStudy.gamification.services.AchievementService;
import ru.nsu.GameStudy.users.dto.StudentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления достижениями.")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/achievements")
    @Operation(description = "Получение списка всех достижений.")
    public List<AchievementDTO> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @PostMapping("/achievements")
    @Operation(description = "Создание нового достижения.")
    public AchievementDTO addAchievement(@RequestBody AchievementDTO request)
            throws ValueAlreadyExistsException {
        return achievementService.createAchievement(request);
    }

    @GetMapping("/achievements/{id}")
    @Operation(description = "Получение достижения по id.")
    public AchievementDTO getAchievement(@PathVariable Long id) {
        return achievementService.getAchievementDTO(id);
    }

    @PutMapping("/achievements/{id}")
    @Operation(description = "Обновление достижения по id.")
    public AchievementDTO updateAchievement(@PathVariable Long id, @RequestBody AchievementDTO request) {
        return achievementService.updateAchievement(id, request);
    }

    @DeleteMapping("/achievements/{id}")
    @Operation(description = "Удаление достижения по id.")
    public ResponseEntity<String> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/achievements/{id}/students")
    @Operation(description = "Получение списка всех студентов, получивших достижение.")
    public List<StudentDTO> getStudentsForAchievement(@PathVariable Long id) {
        return achievementService.getStudentsWithAchievement(id);
    }

    @PutMapping("/achievements/{id}/students")
    @Operation(description = "Добавление достижения студенту.")
    public ResponseEntity<String> addAchievementToStudent(@PathVariable Long id, @RequestParam(name = "student-id") Long studentId) {
        achievementService.addAchievementToStudent(id, studentId);
        return ResponseEntity.ok().build();
    }
}
