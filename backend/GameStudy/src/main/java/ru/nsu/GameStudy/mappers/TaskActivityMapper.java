package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.student_activity.dto.TaskActivityDTO;
import ru.nsu.GameStudy.student_activity.models.TaskActivity;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class TaskActivityMapper {
    @Mapping(target = "taskId", source = "taskActivity.task.id")
    @Mapping(target = "studentId", source = "taskActivity.student.id")
    public abstract TaskActivityDTO toDTO(TaskActivity taskActivity);

    @Mapping(target = "task", source = "dto.taskId")
    @Mapping(target = "student", source = "dto.studentId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateTaskResultFromDTO(
            @MappingTarget TaskActivity result,
            TaskActivityDTO dto);
}
