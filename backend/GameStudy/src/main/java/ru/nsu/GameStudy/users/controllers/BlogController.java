package ru.nsu.GameStudy.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.services.BlogService;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления тегами курсов.")
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/{id}")
    @Operation(description = "Получение \"блога\" по id.")
    public BlogDTO getBlog(@PathVariable Long id) {
        return blogService.getBlogDTO(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление \"блога\".")
    public BlogDTO updateStudentBlog(@PathVariable Long id, @RequestBody BlogDTO request) {
        return blogService.updateBlog(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление \"блога\" по id.")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }
}

