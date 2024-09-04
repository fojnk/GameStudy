package ru.nsu.GameStudy.course_attributes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.services.GameTagService;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления тегами методов геймификации.")
public class GameTagController {
    private final GameTagService tagService;

    @GetMapping("/game-tags")
    @Operation(description = "Получение списка всех тегов.")
    public List<GameTagDTO> getAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping("/game-tags")
    @Operation(description = "Создание нового тега.")
    public GameTagDTO addTag(@RequestBody GameTagDTO request) throws ValueAlreadyExistsException {
        return tagService.createTag(request);
    }

    @GetMapping("/game-tags/{id}")
    @Operation(description = "Получение тега по id.")
    public GameTagDTO getTag(@PathVariable Long id) {
        return tagService.getTagDTO(id);
    }

    @PutMapping("/game-tags/{id}")
    @Operation(description = "Обновление тега по id.")
    public GameTagDTO updateTag(@PathVariable Long id,
                                 @RequestBody GameTagDTO request) {
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/game-tags/{id}")
    @Operation(description = "Удаление тега по id.")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
