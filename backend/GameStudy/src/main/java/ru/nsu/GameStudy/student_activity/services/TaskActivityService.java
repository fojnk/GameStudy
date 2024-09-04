package ru.nsu.GameStudy.student_activity.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.student_activity.dto.TaskActivityDTO;
import ru.nsu.GameStudy.mappers.TaskActivityMapper;
import ru.nsu.GameStudy.student_activity.repositories.TaskActivityRepository;
import ru.nsu.GameStudy.student_activity.models.TaskActivity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskActivityService {
    private final TaskActivityRepository taskActivityRepository;
    private final TaskActivityMapper taskActivityMapper;

    public TaskActivityDTO getTaskActivityDTO(Long resultId) {
        log.info("GET task result with id: {}", resultId);
        return taskActivityMapper.toDTO(getTaskActivity(resultId));
    }

    public TaskActivityDTO getTaskActivityByTaskAndStudent(Long taskId, Long studentId) {
        log.info("GET task result with task {} and student {}", taskId, studentId);
        Optional<TaskActivity> taskResult = taskActivityRepository.findByTask_idAndStudent_id(taskId, studentId);
        if (taskResult.isEmpty()) {
            log.error("task result with task {} and student {} not found", taskId, studentId);
            throw new NotFoundException("task result not found");
        }
        else {
            return taskActivityMapper.toDTO(taskResult.get());
        }
    }

    public List<TaskActivityDTO> getTaskActivitiesByTask(Long taskId) {
        log.info("GET task {} results", taskId);
        return taskActivityRepository
                .findByTask_id(taskId)
                .stream()
                .map(taskActivityMapper::toDTO)
                .toList();
    }

    public List<TaskActivityDTO> getTaskActivitiesInExpRange(Integer min, Integer max) {
        log.info("GET task results with {} <= experience < {}",
                min, max);
        return taskActivityRepository
                .findByExperienceBetween(min, max)
                .stream()
                .map(taskActivityMapper::toDTO)
                .toList();
    }

    public List<TaskActivityDTO> getTaskActivitiesByStudent(Long studentId) {
        log.info("GET task results by student with id {}", studentId);
        return taskActivityRepository
                .findByStudent_id(studentId)
                .stream()
                .map(taskActivityMapper::toDTO)
                .toList();
    }

    public List<TaskActivityDTO> getAllTasksActivities() {
        return taskActivityRepository.findAll().stream()
                .map(taskActivityMapper::toDTO).toList();
    }

    @Transactional
    public void deleteTaskActivity(Long resultId) {
        log.info("DELETE task result with id: {}", resultId);
        if (taskActivityRepository.existsById(resultId)) {
            taskActivityRepository.deleteById(resultId);
        } else {
            log.error("task result with id: {} not found", resultId);
            throw new NotFoundException("task result not found");
        }
    }

    public TaskActivityDTO updateTaskActivity(Long resultId, TaskActivityDTO request) {
        log.info("UPDATE task result with id: {}, request: {experience: {}, time: {}, task: {}, student: {}}",
                resultId, request.getExperience(), request.getTime(), request.getTaskId(), request.getStudentId());
        TaskActivity result = getTaskActivity(resultId);

        taskActivityMapper.updateTaskResultFromDTO(result, request);

        return save(result);
    }

    public TaskActivityDTO createTaskActivity(TaskActivityDTO request) {
        log.info("CREATE task result, request: {experience: {}, time: {}, task: {}, student: {}}",
                request.getExperience(), request.getTime(), request.getTaskId(), request.getStudentId());

        TaskActivity result = TaskActivity.builder().build();
        taskActivityMapper.updateTaskResultFromDTO(result, request);

        return save(result);
    }

    protected TaskActivityDTO save(TaskActivity result) {
        return taskActivityMapper.toDTO(taskActivityRepository.save(result));
    }

    protected TaskActivity getTaskActivity(Long id) {
        log.info("GET task result with id: {}", id);
        Optional<TaskActivity> taskResult = taskActivityRepository.findById(id);
        if (taskResult.isEmpty()) {
            log.error("task result with id: {} not found", id);
            throw new NotFoundException("task result not found");
        }
        else {
            return taskResult.get();
        }
    }
}

