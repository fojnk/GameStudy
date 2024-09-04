package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.users.dto.TeacherRequest;
import ru.nsu.GameStudy.users.models.Teacher;

@Mapper(componentModel = "spring", uses = {
        BlogMapper.class, ReferenceMapper.class, UserMapper.class, MapUtils.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class TeacherMapper {
    @Mapping(target = "user", source = "teacher.userData")
    @Mapping(target = "courseIds", source = "teacher.courses")
    @Mapping(target = "age", source = "teacher.birthDate",
            qualifiedByName = {"MapUtils", "ageFromDate"})
    public abstract TeacherDTO toDTO(Teacher teacher);

    @Mapping(target = "userData", source = "dto.user")
    @Mapping(target = "courses", source = "dto.courseIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateTeacherFromDTO(
            @MappingTarget Teacher teacher,
            TeacherRequest dto);
}