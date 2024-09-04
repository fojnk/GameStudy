package ru.nsu.GameStudy.course_attributes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.course_attributes.services.CourseTagService;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления тегами курсов.")
public class CourseTagController {
    private final CourseTagService tagService;

    @GetMapping("/course-tags")
    @Operation(description = "Получение списка всех тегов.")
    public List<CourseTagDTO> getAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping("/course-tags")
    @Operation(description = "Создание нового тега.")
    public CourseTagDTO addTag(@RequestBody CourseTagDTO request) throws ValueAlreadyExistsException {
        return tagService.createTag(request);
    }

    @GetMapping("/course-tags/{id}")
    @Operation(description = "Получение тега по id.")
    public CourseTagDTO getTag(@PathVariable Long id) {
        return tagService.getTagDTO(id);
    }

    @PutMapping("/course-tags/{id}")
    @Operation(description = "Обновление тега по id.")
    public CourseTagDTO updateTag(@PathVariable Long id,
                                 @RequestBody CourseTagDTO request) {
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/course-tags/{id}")
    @Operation(description = "Удаление тега по id.")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
