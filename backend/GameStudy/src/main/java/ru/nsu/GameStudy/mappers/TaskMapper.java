package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.dto.TaskRequest;
import ru.nsu.GameStudy.courses.models.Task;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class, CourseMapper.class, ReferenceMapper.class, MapUtils.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class TaskMapper {
    public abstract TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    public abstract void updateTaskFromDTO(
            @MappingTarget Task task,
            TaskRequest dto);
}