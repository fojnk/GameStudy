package ru.nsu.GameStudy.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.users.dto.TeacherRequest;
import ru.nsu.GameStudy.users.services.TeacherService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Учителя", description="Общие эндпоинты для работы с учителями.")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/{id}")
    @Operation(description = "Получение учителя по id пользователя.")
    public TeacherDTO getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherByUser(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление учителя по id.")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    @Operation(description = "Получение списка всех учителей.")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping("/")
    @Operation(description = "Добавление учителя.")
    public TeacherDTO addTeacher(@RequestBody TeacherRequest request)
            throws ValueAlreadyExistsException, ParseException {
        return teacherService.createTeacher(request);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление учителя по id.")
    public TeacherDTO updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherRequest request) {
        return teacherService.updateTeacher(id, request);
    }

    @GetMapping("/{id}/blog")
    @Operation(description = "Получение личного блога учителя по id.")
    public BlogDTO getTeacherBlog(@PathVariable Long id) {
        return teacherService.getTeacherBlog(id);
    }

    @GetMapping("/{id}/courses")
    @Operation(description = "Получение всех курсов учителя.")
    public List<CourseDTO> getTeacherCourses(@PathVariable Long id) {
        return teacherService.getCoursesForTeacher(id);
    }

    @PutMapping("/{id}/confirm-status")
    @Operation(description = "Подтверждение статуса преподавателя.")
    public ResponseEntity<String> confirmTeacherStatus(@PathVariable Long id) {
        if (teacherService.confirmTeacherStatus(id)) {
            return ResponseEntity.ok("teacher status confirmed");
        }
        return new ResponseEntity<>("teacher status already confirmed", HttpStatus.BAD_REQUEST);
    }
}
