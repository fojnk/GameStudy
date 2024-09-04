package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentRequest;
import ru.nsu.GameStudy.users.models.Student;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class, BlogMapper.class, ReferenceMapper.class, MapUtils.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class StudentMapper {
    @Mapping(target = "user", source = "student.userData")
    @Mapping(target = "courseIds", source = "student.courses")
    @Mapping(target = "achievementIds", source = "student.achievements")
    @Mapping(target = "groupIds", source = "student.groups")
    @Mapping(target = "age", source = "student.birthDate",
            qualifiedByName = {"MapUtils", "ageFromDate"})
    public abstract StudentDTO toDTO(Student student);

    @Mapping(target = "userData", source = "dto.user")
    @Mapping(target = "courses", source = "dto.courseIds")
    @Mapping(target = "achievements", source = "dto.achievementIds")
    @Mapping(target = "groups", source = "dto.groupIds")
    @Mapping(target = "id", ignore = true)
    public abstract void updateStudentFromDTO(
            @MappingTarget Student student,
            StudentRequest dto);
}
