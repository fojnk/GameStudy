package ru.nsu.GameStudy.gamification.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.gamification.dto.ScoreDTO;
import ru.nsu.GameStudy.gamification.dto.ScoresBoardDTO;
import ru.nsu.GameStudy.gamification.services.ScoresBoardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Рейтинговые таблицы",
        description="Управление рейтинговыми таблицами.")
public class ScoresBoardController {
    private final ScoresBoardService boardService;

    @GetMapping("/boards")
    @Operation(description = "Получение списка всех рейтинговых таблиц.")
    public List<ScoresBoardDTO> getAllBoards() {
        return boardService.getAllBoards();
    }

    @PostMapping("/boards")
    @Operation(description = "Создание новой таблицы рейтингов.")
    public ScoresBoardDTO addBoard(@RequestBody ScoresBoardDTO request) {
        return boardService.createScoresBoard(request);
    }

    @GetMapping("/boards/{id}")
    @Operation(description = "Получение таблицы рейтингов по id.")
    public ScoresBoardDTO getBoard(@PathVariable Long id) {
        return boardService.getScoresBoardDTO(id);
    }

    @PutMapping("/boards/{id}")
    @Operation(description = "Обновление таблицы рейтингов по id.")
    public ScoresBoardDTO updateBoard(@PathVariable Long id, @RequestBody ScoresBoardDTO request) {
        return boardService.updateScoresBoard(id, request);
    }

    @DeleteMapping("/boards/{id}")
    @Operation(description = "Удаление таблицы рейтингов по id.")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoardById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/boards/{id}/scores")
    @Operation(description = "Создание новой записи в таблице рейтингов.")
    public ResponseEntity<String> addScoreToBoard(
            @PathVariable Long id, @RequestBody ScoreDTO request) {
        boardService.addScoreToBoard(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/boards/{id}/scores")
    @Operation(description = "Удаление записи из таблицы рейтингов.")
    public ResponseEntity<String> removeScoreFromBoard(
            @PathVariable Long id, @RequestParam(name = "score-id") Long scoreId) {
        boardService.removeScoreFromBoard(id, scoreId);
        return ResponseEntity.ok().build();
    }
}
