package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.dto.TaskRequest;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.courses.repositories.TaskRepository;
import ru.nsu.GameStudy.mappers.DisciplineMapper;
import ru.nsu.GameStudy.mappers.TaskMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final DisciplineMapper disciplineMapper;

    public TaskDTO createTask(TaskRequest request) {
        log.info("CREATE task, request : {title: {}, answer: {}, experience: {}, complecity: {}, " +
                        "description: {}, time: {}, discipline: {}}", request.getTitle(), request.getAnswer(),
                request.getExperience(), request.getComplexity(), request.getDescription(), request.getTime(), request.getDiscipline());

        Task task = Task.builder().build();
        taskMapper.updateTaskFromDTO(task, request);

        return save(task);
    }

    public TaskDTO getTaskDTO(Long taskId) {
        return taskMapper.toDTO(getTask(taskId));
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::toDTO).toList();
    }

    @Transactional
    public void deleteTask(Long taskId) {
        log.info("DELETE task with id: {}", taskId);
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            log.error("task with id: {} not found", taskId);
            throw new NotFoundException("task not found");
        }
    }

    public TaskDTO updateTask(Long taskId, TaskRequest request) {
        log.info("UPDATE task with id: {}, request : {title: {}, answer: {}, experience: {}, complecity: {}, " +
                        "description: {}, time: {}, discipline: {}}", taskId, request.getTitle(), request.getAnswer(),
                request.getExperience(), request.getComplexity(), request.getDescription(), request.getTime(), request.getDiscipline());
        Task task = getTask(taskId);

        taskMapper.updateTaskFromDTO(task, request);

        return save(task);
    }

    public DisciplineDTO getTaskDiscipline(Long taskId) {
        log.info("GET task discipline, task: {}", taskId);
        Task task = getTask(taskId);
        return disciplineMapper.toDTO(task.getDiscipline());
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
