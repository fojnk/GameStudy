package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.LessonRequest;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.repositories.LessonRepository;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.mappers.GameMethodMapper;
import ru.nsu.GameStudy.mappers.LessonMapper;
import ru.nsu.GameStudy.mappers.TaskMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final GameMethodService methodService;
    private final GameMethodMapper methodMapper;

    public LessonDTO getLessonDTO(Long lessonId) {
        return lessonMapper.toDTO(getLesson(lessonId));
    }

    public List<LessonDTO> getAllLessons() {
        log.info("GET all lessons");
        return lessonRepository.findAll().stream().map(lessonMapper::toDTO).toList();
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        log.info("DELETE lesson with id: {}", lessonId);
        if (lessonRepository.existsById(lessonId)) {
            lessonRepository.deleteById(lessonId);
        } else {
            log.error("lesson with id: {} not found", lessonId);
            throw new NotFoundException("lesson not found");
        }
    }

    @Transactional
    public LessonDTO updateLesson(Long lessonId, LessonRequest request) {
        log.info("UPDATE lesson with id: {}, request: {complexity: {}, " +
                        "title: {}, description: {}, experience: {}, discipline: {}}", lessonId,
                request.getComplexity(), request.getTitle(), request.getDescription(), request.getExperience(),
                request.getDiscipline());
        Lesson lesson = getLesson(lessonId);

        lessonMapper.updateLessonFromDTO(lesson, request);

        return save(lesson);
    }

    @Transactional
    public LessonDTO createLesson(LessonRequest request) {
        log.info("CREATE lesson request: {complexity: {}, " +
                        "title: {}, description: {}, experience: {}, discipline: {}}",
                request.getComplexity(), request.getTitle(), request.getDescription(), request.getExperience(),
                request.getDiscipline());
        Lesson lesson = Lesson.builder().build();
        lessonMapper.updateLessonFromDTO(lesson, request);

        return save(lesson);
    }

    public List<TaskDTO> getTasksInLesson(Long lessonId) {
        log.info("GET tasks in lesson {}", lessonId);
        Lesson lesson = getLesson(lessonId);
        return lesson.getTasks().stream().map(taskMapper::toDTO).toList();
    }

    @Transactional
    public LessonDTO addTaskToLesson(Long lessonId, Long taskId) {
        log.info("ADD task {} to lesson {}", taskId, lessonId);
        Lesson lesson = getLesson(lessonId);
        lesson.getTasks().add(taskService.getTask(taskId));
        return save(lesson);
    }

    @Transactional
    public LessonDTO removeTaskFromLesson(Long lessonId, Long taskId) {
        log.info("REMOVE task {} from lesson {}", lessonId, taskId);
        Lesson lesson = getLesson(lessonId);
        lesson.getTasks().remove(taskService.getTask(taskId));
        return save(lesson);
    }

    public List<LessonDTO> getTaskLessons(Long taskId) {
        log.info("GET lessons dependent on task {}", taskId);
        return lessonRepository.findByTasks_id(taskId)
                .stream()
                .map(lessonMapper::toDTO)
                .toList();
    }

    public List<GameMethodDTO> getLessonGameMethods(Long lessonId) {
        log.info("GET lesson game methods, lesson: {}", lessonId);
        Lesson lesson = getLesson(lessonId);
        return lesson.getGameMethods().stream()
                .map(methodMapper::toDTO).toList();
    }

    @Transactional
    public LessonDTO addGameMethodsToLesson(Long lessonId, List<Long> gameMethodIds) {
        log.info("ADD game methods {} to lesson: {}", gameMethodIds, lessonId);
        Lesson lesson = getLesson(lessonId);
        List<Long> exceptIds = lesson.getGameMethods().stream().map(GameMethod::getId).toList();
        List<Long> ids = gameMethodIds
                .stream()
                .filter(x ->
                        !exceptIds.contains(x))
                .toList();
        lesson
                .getGameMethods()
                .addAll(ids.stream().map(methodService::getGameMethod).toList());
        return save(lesson);
    }

    @Transactional
    public LessonDTO removeGameMethodsFromLesson(Long lessonId, List<Long> gameMethodIds) {
        log.info("REMOVE game methods {} from lesson: {}", gameMethodIds, lessonId);
        Lesson lesson = getLesson(lessonId);
        lesson
                .getGameMethods()
                .removeAll(gameMethodIds.stream().map(methodService::getGameMethod).toList());
        return save(lesson);
    }

    protected LessonDTO save(Lesson lesson) {
        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }

    protected Lesson getLesson(Long id) {
        log.info("GET lesson with id: {}", id);
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isEmpty()) {
            log.error("lesson with id: {} not found", id);
            throw new NotFoundException("lesson not found");
        }
        else {
            return lesson.get();
        }
    }
}
