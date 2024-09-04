package ru.nsu.GameStudy.course_attributes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.course_attributes.services.DisciplineService;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.services.CourseService;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Управление игровыми сущностями",
        description="Управления дисциплинами.")
public class DisciplineController {
    private final DisciplineService disciplineService;
    private final CourseService courseService;

    @GetMapping("/disciplines")
    @Operation(description = "Получение списка всех дисциплин.")
    public List<DisciplineDTO> getAllDisciplines() {
        return disciplineService.getAllDisciplines();
    }

    @PostMapping("/disciplines")
    @Operation(description = "Создание новой дисциплины.")
    public DisciplineDTO addDiscipline(@RequestBody DisciplineDTO request) throws ValueAlreadyExistsException {
        return disciplineService.createDiscipline(request);
    }

    @GetMapping("/disciplines/{id}")
    @Operation(description = "Получение дисциплины по id.")
    public DisciplineDTO getDiscipline(@PathVariable Long id) {
        return disciplineService.getDisciplineDTO(id);
    }

    @PutMapping("/disciplines/{id}")
    @Operation(description = "Обновление дисциплины по id.")
    public DisciplineDTO updateDiscipline(@PathVariable Long id,
                                        @RequestBody DisciplineDTO request) {
        return disciplineService.updateDiscipline(id, request);
    }

    @DeleteMapping("/disciplines/{id}")
    @Operation(description = "Удаление дисциплины по id.")
    public ResponseEntity<String> deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/disciplines/{id}/courses")
    @Operation(description = "Получение списка курсов по дисциплине.")
    public List<CourseDTO> getCoursesByDiscipline(@PathVariable Long id) {
        return courseService.getCoursesByDiscipline(id);
    }
}
