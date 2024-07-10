package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.TaskDTO;
import ru.nsu.GameStudy.models.Task;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDiscipline().getId(),
                task.getTime(),
                task.getExperience(),
                task.getComplexity(),
                task.getAnswer()
        );
    }
}