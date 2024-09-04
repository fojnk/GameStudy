package ru.nsu.GameStudy.courses.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.courses.dto.CourseRequest;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.services.CourseService;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentGroupDTO;
import ru.nsu.GameStudy.users.dto.TeacherDTO;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Курсы", description="Общие эндпоинты для работы с курсами.")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/")
    @Operation(description = "Получение списка всех курсов.")
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/")
    @Operation(description = "Создание курса.")
    public CourseDTO createCourse(@RequestBody CourseRequest request) {
        return courseService.createCourse(request);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление курса.")
    public CourseDTO updateCourse(@PathVariable Long id,
                                  @RequestBody CourseRequest request) {
        return courseService.updateCourse(id, request);
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение курса по id.")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseDTO(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление курса по id.")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rating")
    @Operation(description = "Получение курсов в диапазоне рейтинга.")
    public List<CourseDTO> getCoursesByRatingInRange(
            @RequestParam Double min, @RequestParam Double max) {
        return courseService.getAllCoursesByRatingInRange(min, max);
    }

    @GetMapping("/cost")
    @Operation(description = "Получение курсов в диапазоне цены.")
    public List<CourseDTO> getCoursesByCostInRange(
            @RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return courseService.getAllCoursesByCostInRange(min, max);
    }

    @GetMapping("/creator/{id}")
    @Operation(description = "Получение курсов по id создателя курса.")
    public List<CourseDTO> getCoursesByCreator(@PathVariable Long id) {
        return courseService.getCoursesByCreator(id);
    }

    @GetMapping("/creator")
    @Operation(description = "Получение создателя курса.")
    public UserDTO getCourseCreator(@RequestParam Long id) {
        return courseService.getCourseCreator(id);
    }

    @GetMapping("/{id}/lessons")
    @Operation(description = "Получение списка всех уроков в курсе.")
    public List<LessonDTO> getCourseLessons(@PathVariable Long id) {
        return courseService.getCourseLessons(id);
    }

    @PutMapping("/{id}/lessons")
    @Operation(description = "Добавление урока на курс.")
    public ResponseEntity<String> addLessonToCourse(@PathVariable Long id, @RequestParam(name = "lesson-id") Long lessonId) {
        courseService.addLessonToCourse(id, lessonId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/lessons")
    @Operation(description = "Удаление урока с курса.")
    public ResponseEntity<String> removeLessonFromCourse(@PathVariable Long id, @RequestParam(name = "lesson-id") Long lessonId) {
        courseService.removeLessonFromCourse(id, lessonId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/disciplines")
    @Operation(description = "Получение списка всех дисциплин в курсе.")
    public List<DisciplineDTO> getCourseDisciplines(@PathVariable Long id) {
        return courseService
                .getCourseLessons(id)
                .stream()
                .map(LessonDTO::getDiscipline)
                .toList();
    }

    @GetMapping("/{id}/tasks")
    @Operation(description = "Получение списка всех задач в курсе.")
    public List<TaskDTO> getCourseTasks(@PathVariable Long id) {
        return courseService.getCourseTasks(id);
    }

    @PutMapping("/{id}/tasks")
    @Operation(description = "Добавление задачи на курс.")
    public ResponseEntity<String> addTaskToCourse(@PathVariable Long id, @RequestParam(name = "task-id") Long taskId) {
        courseService.addTaskToCourse(id, taskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/tasks")
    @Operation(description = "Удаление задачи с курса.")
    public ResponseEntity<String> removeTaskFromCourse(@PathVariable Long id, @RequestParam(name = "task-id") Long taskId) {
        courseService.removeTaskFromCourse(id, taskId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/tags")
    @Operation(description = "Получение списка всех тегов в курсе.")
    public List<CourseTagDTO> getCourseTags(@PathVariable Long id) {
        return courseService.getTagsInCourse(id);
    }

    @PutMapping("/{id}/tags")
    @Operation(description = "Добавление тега на курс.")
    public ResponseEntity<String> addTagToCourse(@PathVariable Long id, @RequestParam(name = "tag-id") Long tagId) {
        courseService.addTagToCourse(id, tagId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/tags")
    @Operation(description = "Удаление тега с курса.")
    public ResponseEntity<String> removeTagFromCourse(@PathVariable Long id, @RequestParam(name = "tag-id") Long tagId) {
        courseService.removeTagFromCourse(id, tagId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/students")
    @Operation(description = "Получение списка всех студентов на курсе.")
    public List<StudentDTO> getStudentsOnCourse(@PathVariable Long id) {
        return courseService.getStudentsOnCourse(id);
    }

    @PutMapping("/{id}/students")
    @Operation(description = "Запись студента на курс.")
    public ResponseEntity<String> addStudentToCourse(
            @PathVariable Long id,
            @RequestBody List<Long> studentIds) {
        courseService.addStudentsToCourse(id, studentIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/students")
    @Operation(description = "Удаление студента с курса.")
    public ResponseEntity<String> deleteStudentFromCourse(
            @PathVariable Long id,
            @RequestBody List<Long> studentIds) {
        courseService.removeStudentsFromCourse(id, studentIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/teachers")
    @Operation(description = "Получение списка всех учителей на курсе.")
    public List<TeacherDTO> getTeachersOnCourse(@PathVariable Long id) {
        return courseService.getTeachersOnCourse(id);
    }

    @PutMapping("/{id}/teachers")
    @Operation(description = "Запись учителя на курс.")
    public ResponseEntity<String> addTeachersToCourse(
            @PathVariable Long id,
            @RequestBody List<Long> teacherIds) {
        courseService.addTeachersToCourse(id, teacherIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/teachers")
    @Operation(description = "Удаление учителя с курса.")
    public ResponseEntity<String> deleteTeachersFromCourse(
            @PathVariable Long id,
            @RequestBody List<Long> teacherIds) {
        courseService.removeTeachersFromCourse(id, teacherIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/groups")
    @Operation(description = "Получение списка всех групп студентов на курсе.")
    public List<StudentGroupDTO> getGroupsOnCourse(@PathVariable Long id) {
        return courseService.getGroupsOnCourse(id);
    }

    @PutMapping("/{id}/groups")
    @Operation(description = "Запись группы на курс.")
    public ResponseEntity<String> addGroupsToCourse(
            @PathVariable Long id,
            @RequestBody List<Long> groupIds) {
        courseService.addGroupsToCourse(id, groupIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/groups")
    @Operation(description = "Удаление группы с курса.")
    public ResponseEntity<String> deleteGroupsFromCourse(
            @PathVariable Long id,
            @RequestBody List<Long> groupIds) {
        courseService.removeGroupsFromCourse(id, groupIds);
        return ResponseEntity.ok().build();
    }
}
