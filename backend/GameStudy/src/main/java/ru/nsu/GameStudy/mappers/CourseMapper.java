package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.dto.CourseRequest;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.mappers.CourseTagMapper;
import ru.nsu.GameStudy.mappers.ReferenceMapper;
import ru.nsu.GameStudy.mappers.UserMapper;

@Mapper(componentModel = "spring", uses = {
        CourseTagMapper.class, UserMapper.class, ReferenceMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CourseMapper {
    @Mapping(target = "creatorUId", source = "course.creator.id")
    @Mapping(target = "tagIds", source = "course.tags")
    @Mapping(target = "lessonIds", source = "course.lessons")
    @Mapping(target = "taskIds", source = "course.tasks")
    @Mapping(target = "boardIds", source = "course.boards")
    @Mapping(target = "teacherIds", source = "course.teachers")
    @Mapping(target = "studentIds", source = "course.students")
    @Mapping(target = "groupIds", source = "course.groups")
    public abstract CourseDTO toDTO(Course course);

    @Mapping(target = "creator", source = "courseDTO.creatorUId")
    @Mapping(target = "tags", source = "courseDTO.tagIds")
    @Mapping(target = "lessons", source = "courseDTO.lessonIds")
    @Mapping(target = "tasks", source = "courseDTO.taskIds")
    @Mapping(target = "students", source = "courseDTO.studentIds")
    @Mapping(target = "teachers", source = "courseDTO.teacherIds")
    @Mapping(target = "groups", source = "courseDTO.groupIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateCourseFromDTO(
            @MappingTarget Course course,
            CourseRequest courseDTO);
}