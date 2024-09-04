package ru.nsu.GameStudy.student_activity.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.student_activity.dto.LessonActivityDTO;
import ru.nsu.GameStudy.mappers.LessonActivityMapper;
import ru.nsu.GameStudy.student_activity.repositories.LessonActivityRepository;
import ru.nsu.GameStudy.student_activity.models.LessonActivity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonActivityService {
    private final LessonActivityRepository activityRepository;
    private final LessonActivityMapper activityMapper;

    public LessonActivityDTO getLessonActivityDTO(Long resultId) {
        log.info("GET lesson activity with id: {}", resultId);
        return activityMapper.toDTO(getLessonActivity(resultId));
    }

    public LessonActivityDTO getLessonActivityByLessonAndStudent(Long taskId, Long studentId) {
        log.info("GET lesson activity with lesson {} and student {}", taskId, studentId);
        Optional<LessonActivity> taskResult = activityRepository.findByLesson_idAndStudent_id(taskId, studentId);
        if (taskResult.isEmpty()) {
            log.error("lesson activity with lesson {} and student {} not found", taskId, studentId);
            throw new NotFoundException("lesson activity not found");
        }
        else {
            return activityMapper.toDTO(taskResult.get());
        }
    }

    public List<LessonActivityDTO> getActivityByLesson(Long taskId) {
        log.info("GET lesson {} activity", taskId);
        return activityRepository
                .findByLesson_id(taskId)
                .stream()
                .map(activityMapper::toDTO)
                .toList();
    }

    public List<LessonActivityDTO> getActivityInExpRange(Integer min, Integer max) {
        log.info("GET lesson activity with {} <= experience < {}",
                min, max);
        return activityRepository
                .findByExperienceBetween(min, max)
                .stream()
                .map(activityMapper::toDTO)
                .toList();
    }

    public List<LessonActivityDTO> getActivityByStudent(Long studentId) {
        log.info("GET lesson activity by student with id {}", studentId);
        return activityRepository
                .findByStudent_id(studentId)
                .stream()
                .map(activityMapper::toDTO)
                .toList();
    }

    public List<LessonActivityDTO> getAllLessonActivity() {
        return activityRepository.findAll().stream()
                .map(activityMapper::toDTO).toList();
    }

    @Transactional
    public void deleteLessonActivity(Long resultId) {
        log.info("DELETE lesson activity with id: {}", resultId);
        if (activityRepository.existsById(resultId)) {
            activityRepository.deleteById(resultId);
        } else {
            log.error("lesson activity with id: {} not found", resultId);
            throw new NotFoundException("lesson activity not found");
        }
    }

    public LessonActivityDTO updateLessonActivity(Long resultId, LessonActivityDTO request) {
        log.info("UPDATE lesson activity with id: {}, request: {experience: {}, time: {}, lesson: {}, student: {}}",
                resultId, request.getExperience(), request.getTime(), request.getLessonId(), request.getStudentId());
        LessonActivity result = getLessonActivity(resultId);

        activityMapper.updateLessonActivityFromDTO(result, request);

        return save(result);
    }

    public LessonActivityDTO createLessonActivity(LessonActivityDTO request) {
        log.info("CREATE lesson activity, request: {experience: {}, time: {}, task: {}, student: {}}",
                request.getExperience(), request.getTime(), request.getLessonId(), request.getStudentId());

        LessonActivity result = LessonActivity.builder().build();
        activityMapper.updateLessonActivityFromDTO(result, request);

        return save(result);
    }

    protected LessonActivityDTO save(LessonActivity result) {
        return activityMapper.toDTO(activityRepository.save(result));
    }

    protected LessonActivity getLessonActivity(Long id) {
        log.info("GET lesson activity with id: {}", id);
        Optional<LessonActivity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            log.error("lesson activity with id: {} not found", id);
            throw new NotFoundException("lesson activity not found");
        }
        else {
            return activity.get();
        }
    }
}

