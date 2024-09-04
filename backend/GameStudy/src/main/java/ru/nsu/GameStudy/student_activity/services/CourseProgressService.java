package ru.nsu.GameStudy.student_activity.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.mappers.CourseMapper;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.mappers.*;
import ru.nsu.GameStudy.student_activity.dto.CourseProgressDTO;
import ru.nsu.GameStudy.student_activity.models.CourseProgress;
import ru.nsu.GameStudy.student_activity.repositories.CourseProgressRepository;
import ru.nsu.GameStudy.users.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseProgressService {
    private final CourseProgressRepository progressRepository;
    private final CourseProgressMapper progressMapper;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @Transactional
    public CourseProgressDTO createCourseProgress(CourseProgressDTO request) {
        log.info("CREATE course progress, request: {reached_exp: {}, course: {}, " +
                        "student: {}}", request.getReachedExp(),
                request.getCourseId(), request.getStudentId());
        CourseProgress progress = CourseProgress.builder().build();
        progressMapper.updateCourseProgressFromDTO(progress, request);

        return save(progress);
    }

    public List<CourseProgressDTO> getAllCourseProgress() {
        log.info("GET all course progress");
        return progressRepository.findAll().stream().map(progressMapper::toDTO).toList();
    }

    public CourseProgressDTO getCourseProgressDTO(Long id) {
        return progressMapper.toDTO(getCourseProgress(id));
    }

    @Transactional
    public void deleteCourseProgress(Long id) {
        log.info("DELETE course progress with id: {}", id);
        if (progressRepository.existsById(id)) {
            progressRepository.deleteById(id);
        } else {
            log.error("course progress with id: {} not found", id);
            throw new NotFoundException("course progress not found");
        }
    }

    @Transactional
    public CourseProgressDTO updateCourseProgress(Long id, CourseProgressDTO request) {
        log.info("UPDATE courseProgress, request: {reached_exp: {}, course: {}, " +
                        "student: {}}", request.getReachedExp(),
                request.getCourseId(), request.getStudentId());
        CourseProgress progress = getCourseProgress(id);

        progressMapper.updateCourseProgressFromDTO(progress, request);

        return save(progress);
    }

    @Transactional
    public CourseDTO getCourseFromCourseProgress(Long id) {
        log.info("GET course from course progress {} ", id);
        CourseProgress progress = getCourseProgress(id);
        return courseMapper.toDTO(progress.getCourse());
    }

    @Transactional
    public StudentDTO getStudentFromCourseProgress(Long id) {
        log.info("GET student from course progress {} ", id);
        CourseProgress progress = getCourseProgress(id);
        return studentMapper.toDTO(progress.getStudent());
    }

    @Transactional
    public List<TaskDTO> getPassedTasks(Long id) {
        log.info("GET passed tasks from course progress {} ", id);
        CourseProgress progress = getCourseProgress(id);
        return progress.getPassedTasks().stream()
                .map(taskMapper::toDTO).toList();
    }

    @Transactional
    public CourseProgressDTO addTaskAsPassed(Long id, Long taskId) {
        log.info("ADD passed task {} to course progress {} ", taskId, id);
        CourseProgress progress = getCourseProgress(id);
        progress.getPassedTasks().add(taskService.getTask(taskId));
        return save(progress);
    }

    @Transactional
    public CourseProgressDTO removeTaskFromPassed(Long id, Long taskId) {
        log.info("REMOVE passed task {} from course progress {} ", taskId, id);
        CourseProgress progress = getCourseProgress(id);
        progress.getPassedTasks().remove(taskService.getTask(taskId));
        return save(progress);
    }

    @Transactional
    public List<LessonDTO> getPassedLessons(Long id) {
        log.info("GET passed lessons from course progress {} ", id);
        CourseProgress progress = getCourseProgress(id);
        return progress.getPassedLessons().stream()
                .map(lessonMapper::toDTO).toList();
    }

    @Transactional
    public CourseProgressDTO addLessonAsPassed(Long id, Long lessonId) {
        log.info("ADD passed lesson {} to course progress {} ", lessonId, id);
        CourseProgress progress = getCourseProgress(id);
        progress.getPassedLessons().add(lessonService.getLesson(lessonId));
        return save(progress);
    }

    @Transactional
    public CourseProgressDTO removeLessonFromPassed(Long id, Long lessonId) {
        log.info("REMOVE passed lesson {} from course progress {} ", lessonId, id);
        CourseProgress progress = getCourseProgress(id);
        progress.getPassedLessons().remove(lessonService.getLesson(lessonId));
        return save(progress);
    }

    @Transactional
    public List<CourseProgressDTO> getAllProgressByCourse(Long courseId) {
        log.info("GET all course progress in course {} ", courseId);
        List<CourseProgress> progressList =
                progressRepository.findByCourse_id(courseId);
        return progressList.stream().map(progressMapper::toDTO).toList();
    }

    @Transactional
    public List<CourseProgressDTO> getAllProgressByStudent(Long studentId) {
        log.info("GET all courses progress for student {} ", studentId);
        List<CourseProgress> progressList =
                progressRepository.findByStudent_id(studentId);
        return progressList.stream().map(progressMapper::toDTO).toList();
    }

    @Transactional
    public CourseProgressDTO getProgressByCourseAndStudent(Long courseId, Long studentId) {
        log.info("GET all courses progress in course {} for student {} ",
                courseId, studentId);
        Optional<CourseProgress> progress =
                progressRepository.findByCourse_idAndStudent_id(courseId, studentId);
        if (progress.isEmpty()) {
            log.error("no progress for student {} found", studentId);
            throw new NotFoundException("no progress found");
        }
        return progressMapper.toDTO(progress.get());
    }

    protected CourseProgressDTO save(CourseProgress progress) {
        return progressMapper.toDTO(progressRepository.save(progress));
    }

    protected CourseProgress getCourseProgress(Long id) {
        log.info("GET course progress by id: {}", id);
        Optional<CourseProgress> progress = progressRepository.findById(id);
        if (progress.isEmpty()) {
            log.error("course progress with id: {} not found", id);
            throw new NotFoundException("course progress not found");
        }
        else {
            return progress.get();
        }
    }
}
