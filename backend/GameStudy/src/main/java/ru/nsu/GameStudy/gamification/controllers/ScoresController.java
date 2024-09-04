package ru.nsu.GameStudy.gamification.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.gamification.dto.ScoreDTO;
import ru.nsu.GameStudy.gamification.services.ScoreService;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Рейтинги",
        description="Управление рейтингами.")
public class ScoresController {
    private final ScoreService scoreService;

    @PostMapping("/scores")
    @Operation(description = "Создание новой записи рейтингов.")
    public ScoreDTO addScore(@RequestBody ScoreDTO request) {
        return scoreService.createScore(request);
    }

    @GetMapping("/scores/{id}")
    @Operation(description = "Получение записи рейтингов по id.")
    public ScoreDTO getScore(@PathVariable Long id) {
        return scoreService.getScoreDTO(id);
    }

    @PutMapping("/scores/{id}")
    @Operation(description = "Обновление записи рейтингов.")
    public ScoreDTO updateScore(@PathVariable Long id, @RequestBody ScoreDTO request) {
        return scoreService.updateScore(id, request);
    }

    @DeleteMapping("/scores/{id}")
    @Operation(description = "Удаление записи рейтингов.")
    public ResponseEntity<String> deleteScore(@PathVariable Long id) {
        scoreService.deleteScoreById(id);
        return ResponseEntity.ok().build();
    }
}
