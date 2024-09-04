package ru.nsu.GameStudy.gamification.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.GameStudy.gamification.dto.ScoresBoardDTO;
import ru.nsu.GameStudy.gamification.services.ScoresBoardService;

import java.util.List;


@RestController("GamificationStudentController")
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Рейтинг-таблицы студентов")
public class StudentController {
    private final ScoresBoardService boardService;

    @GetMapping("/{id}/boards")
    @Operation(description = "Получение рейтинг-таблиц студента.")
    public List<ScoresBoardDTO> getScoresBoardsByStudent(@PathVariable Long id) {
        return boardService.getScoresBoardsByStudent(id);
    }
}
