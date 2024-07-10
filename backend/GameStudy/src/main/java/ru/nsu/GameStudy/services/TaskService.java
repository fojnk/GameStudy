package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.TaskDTO;
import ru.nsu.GameStudy.models.Task;
import ru.nsu.GameStudy.repository.TaskRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final DisciplineService disciplineService;

    public Task createTask(TaskDTO request) {

        var discipline = disciplineService.getDiscipline(request.getDisciplineId());

        Task task = Task.builder()
                .title(request.getTitle())
                .answer(request.getAnswer())
                .experience(request.getExperience())
                .complexity(request.getComplexity())
                .description(request.getDescription())
                .time(request.getTime())
                .discipline(discipline)
                .build();

        return taskRepository.save(task);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(Long taskId) {
        Task task = getTask(taskId);
        taskRepository.delete(task);
    }

    public Task updateTask(Long taskId, TaskDTO request) {
        Task task = getTask(taskId);

        task.setAnswer(request.getAnswer());
        task.setComplexity(request.getComplexity());
        task.setTime(request.getTime());
        task.setDescription(request.getDescription());
        task.setExperience(request.getExperience());

        return taskRepository.save(task);
    }
}