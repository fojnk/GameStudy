package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.StudentDTO;
import ru.nsu.GameStudy.models.Student;

public class StudentMapper {
    public static StudentDTO toDto(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getUserData().getId(),
                student.getWallet(),
                student.getExperience(),
                student.getActivity(),
                student.getBirthDate(),
                BlogMapper.toDTO(student.getBlog())
        );
    }
}
