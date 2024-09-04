package ru.nsu.GameStudy.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.dto.ScoresBoardDTO;
import ru.nsu.GameStudy.users.dto.*;
import ru.nsu.GameStudy.users.services.StudentGroupService;
import ru.nsu.GameStudy.users.services.StudentService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Студенты", description="Общие эндпоинты для работы со студентами.")
public class StudentController {
    private final StudentService studentService;
    private final StudentGroupService groupService;

    @GetMapping("/{id}")
    @Operation(description = "Получение студента по id пользователя.")
    public StudentDTO getStudentByUserId(@PathVariable Long id) {
        return studentService.getStudentByUser(id);
    }

    @GetMapping("/")
    @Operation(description = "Получение списка всех студентов.")
    public List<StudentDTO> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/")
    @Operation(description = "Добавление студента.")
    public StudentDTO addStudent(@RequestBody StudentRequest request)
            throws ValueAlreadyExistsException, ParseException {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновление студента по id.")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление студента по id.")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/blog")
    @Operation(description = "Получение \"блога\" студента.")
    public BlogDTO getStudentBlog(@PathVariable Long id) {
        return studentService.getStudentBlog(id);
    }

    @GetMapping("/{id}/courses")
    @Operation(description = "Получение всех курсов студента.")
    public List<CourseDTO> getStudentCourses(@PathVariable Long id) {
        return studentService.getCoursesForStudent(id);
    }

    @GetMapping("/{id}/groups")
    @Operation(description = "Получение всех групп, в которые входит студент.")
    public List<StudentGroupDTO> getStudentGroups(@PathVariable Long id) {
        return groupService.getGroupsForStudent(id);
    }

    @GetMapping("/groups/{id}")
    @Operation(description = "Получение группы по id.")
    public StudentGroupDTO getStudentGroupById(@PathVariable Long id) {
        return groupService.getStudentGroupDTO(id);
    }

    @GetMapping("/groups")
    @Operation(description = "Получение групп студентов.")
    public List<StudentGroupDTO> getAllStudentGroups(@RequestParam(name = "creator-id") Long creatorId) {
        if (creatorId != null) {
            return groupService.getGroupsByCreator(creatorId);
        }
        return groupService.getAllStudentGroups();
    }

    @PostMapping("/groups")
    @Operation(description = "Создание группы студентов.")
    public ResponseEntity<String> addStudentGroup(@RequestBody StudentGroupRequest request) {
        groupService.createStudentGroup(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/groups/{id}")
    @Operation(description = "Обновление группы студентов по id.")
    public ResponseEntity<String> updateStudentGroup(@PathVariable Long id, @RequestBody StudentGroupRequest request) {
        groupService.updateStudentGroup(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/groups/{id}")
    @Operation(description = "Удаление группы студентов по id.")
    public ResponseEntity<String> deleteStudentGroup(@PathVariable Long id) {
        groupService.deleteStudentGroup(id);
        return ResponseEntity.ok().build();
    }
}

