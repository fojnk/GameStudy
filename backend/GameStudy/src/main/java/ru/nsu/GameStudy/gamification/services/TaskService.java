package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.courses.repositories.TaskRepository;
import ru.nsu.GameStudy.mappers.TaskMapper;

import java.util.List;
import java.util.Optional;

@Service("GamificationTaskService")
@Slf4j
@RequiredArgsConstructor
class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final GameMethodService methodService;

    public List<TaskDTO> getTasksByGameMethod(Long methodId) {
        log.info("GET tasks by game method {}", methodId);
        return taskRepository
                .findByGameMethods_id(methodId)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    protected TaskDTO save(Task task) {
        return taskMapper.toDTO(taskRepository.save(task));
    }

    protected Task getTask(Long id) {
        log.info("GET task id: {}", id);
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            log.error("task with id: {} not found", id);
            throw new NotFoundException("task not found");
        }
        else {
            return task.get();
        }
    }
}
