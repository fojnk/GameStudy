package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.TeacherDTO;
import ru.nsu.GameStudy.models.Teacher;

public class TeacherMapper {
    public static TeacherDTO toDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getUserData().getId(),
                teacher.getOrganisation(),
                teacher.getQualification(),
                BlogMapper.toDTO(teacher.getBlog()),
                teacher.getBirthDate()
        );
    }
}