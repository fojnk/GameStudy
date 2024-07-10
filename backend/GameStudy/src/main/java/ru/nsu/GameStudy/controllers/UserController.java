package ru.nsu.GameStudy.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.GameStudy.dto.UserDTO;
import ru.nsu.GameStudy.dto.mappers.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.GameStudy.models.*;
import ru.nsu.GameStudy.repository.CourseRepository;
import ru.nsu.GameStudy.repository.LessonRepository;
import ru.nsu.GameStudy.repository.TaskRepository;
import ru.nsu.GameStudy.repository.UserRepository;
import ru.nsu.GameStudy.services.ScoresService;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name="Пользователи", description="Общие эндпоинты для работы с пользователями.")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoresService scoresService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDto(value))).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // @GetMapping("/{username}")
    // public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
    //     try {
    //         User user = userRepository.findByUsername(username).orElse(null);
    //         if (user != null) {
    //             return ResponseEntity.ok(user.getUsername());
    //         } else {
    //             return ResponseEntity.badRequest().body("User not found");
    //         }

    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("Bad Request");
    //     }
    // }


    @GetMapping("/scoresByStudentId/{id}")
    public ResponseEntity<List<Scores>> getScoresByStudentId(@PathVariable Long id) {
        Optional<List<Scores>> scores = scoresService.getScoresByStudentId(id);
        if (scores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(scores.get());
        }
    }

    @GetMapping("/scoresByCourseId/{id}")
    public ResponseEntity<List<Scores>> getScoresByCourseId(@PathVariable Long id) {
        Optional<List<Scores>> scores = scoresService.getScoresByCourseId(id);
        if (scores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(scores.get());
        }
    }

    @GetMapping("/coursesByCreator/{id}")
    public ResponseEntity<List<Course>> getCoursesByCreatorId(@PathVariable Long id) {
        Optional<List<Course>> courses = courseRepository.findByCreator_id(id);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(courses.get());
        }
    }

    @GetMapping("/coursesByStudentId/{id}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable Long id) {
        Optional<List<Course>> courses = courseRepository.findByStudentId(id);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(courses.get());
        }
    }

    @GetMapping("/lessonsByDiscipline/{disciplineId}")
    public ResponseEntity<List<Lesson>> getLessonsByDiscipline(@PathVariable Integer disciplineId) {
        Optional<List<Lesson>> lessons = lessonRepository.findByDiscipline_id(disciplineId);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(lessons.get());
        }
    }

    @GetMapping("/lessonsByCourse/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable Long courseId) {
        Optional<List<Lesson>> lessons = lessonRepository.findByCourseId(courseId);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(lessons.get());
        }
    }

    @GetMapping("/tasksByCourse/{courseId}")
    public ResponseEntity<List<Task>> getTasksByCourse(@PathVariable Long courseId) {
        Optional<List<Task>> tasks = taskRepository.findByCourseId(courseId);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(tasks.get());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(UserMapper::toDto).toList());
    }
}