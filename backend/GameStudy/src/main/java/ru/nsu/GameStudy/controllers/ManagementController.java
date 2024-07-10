package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.dto.AchievementDTO;
import ru.nsu.GameStudy.dto.GameMethodDTO;
import ru.nsu.GameStudy.dto.mappers.AchievementMapper;
import ru.nsu.GameStudy.dto.mappers.GameMethodMapper;
import ru.nsu.GameStudy.response.ApiResponse;
import ru.nsu.GameStudy.services.AchievementService;
import ru.nsu.GameStudy.services.GameMethodService;

import java.util.List;

@RestController
@RequestMapping("/api/management")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления достижения, методами геймификации, тегами.")
public class ManagementController {

    private final AchievementService achievementService;
    private final GameMethodService gameMethodService;

    @GetMapping("/achievements/")
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        var achievements = achievementService.getAllAchievements()
                .stream().map(AchievementMapper::toDTO)
                .toList();
        return ResponseEntity.ok(achievements);
    }

    @PostMapping("/achievements/")
    public ResponseEntity<ApiResponse> addAchievement(@RequestBody AchievementDTO request) {
        try {
            achievementService.createAchievement(request);
            return ResponseEntity.ok(new ApiResponse(true, "achievement created"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @GetMapping("/achievements/{id}")
    public ResponseEntity<AchievementDTO> getAchievement(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(AchievementMapper.toDTO(achievementService.getAchievement(id)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/achievements/{id}")
    public ResponseEntity<ApiResponse> updateAchievement(@PathVariable Long id,
                                                            @RequestBody AchievementDTO request) {
        try {
            achievementService.updateAchievement(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "achievement updated"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<ApiResponse> deleteAchievement(@PathVariable Long id) {
        try {
            achievementService.deleteAchievement(id);
            return ResponseEntity.ok(new ApiResponse(true, "achievement deleted"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @GetMapping("/game-methods/")
    public ResponseEntity<List<GameMethodDTO>> getAllGameMethods() {
        var methods = gameMethodService.getAllGameMethods()
                .stream().map(GameMethodMapper::toDTO)
                .toList();
        return ResponseEntity.ok(methods);
    }

    @PostMapping("/game-methods/")
    public ResponseEntity<ApiResponse> addGameMethod(@RequestBody GameMethodDTO request) {
        try {
            gameMethodService.createGameMethod(request);
            return ResponseEntity.ok(new ApiResponse(true, "game method created"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @GetMapping("/game-methods/{id}")
    public ResponseEntity<GameMethodDTO> getGameMethod(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(GameMethodMapper.toDTO(gameMethodService.getGameMethod(id)));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/game-methods/{id}")
    public ResponseEntity<ApiResponse> updateGameMethod(@PathVariable Integer id,
                                                         @RequestBody GameMethodDTO request) {
        try {
            gameMethodService.updateGameMethod(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "game method updated"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }

    @DeleteMapping("/game-methods/{id}")
    public ResponseEntity<ApiResponse> deleteGameMethod(@PathVariable Integer id) {
        try {
            gameMethodService.deleteGameMethod(id);
            return ResponseEntity.ok(new ApiResponse(true, "game method deleted"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse(false, e.toString())
            );
        }
    }
 }