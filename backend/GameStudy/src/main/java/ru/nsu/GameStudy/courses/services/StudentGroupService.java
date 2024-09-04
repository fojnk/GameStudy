package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.mappers.StudentGroupMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentGroupDTO;
import ru.nsu.GameStudy.users.models.StudentGroup;
import ru.nsu.GameStudy.users.repositories.StudentGroupRepository;

import java.util.List;
import java.util.Optional;

@Service("CoursesStudentGroupService")
@Slf4j
@RequiredArgsConstructor
public class StudentGroupService {
    private final StudentGroupRepository groupRepository;
    private final StudentGroupMapper groupMapper;
    private final StudentMapper studentMapper;

    public List<StudentDTO> getStudentsInGroup(Long id) {
        log.info("GET students in group {}", id);
        StudentGroup group = getStudentGroup(id);
        return group.getStudents().stream().map(studentMapper::toDTO).toList();
    }

    protected StudentGroupDTO save(StudentGroup group) {
        return groupMapper.toDTO(groupRepository.save(group));
    }

    protected StudentGroup getStudentGroup(Long id) {
        log.info("GET group with id: {}", id);
        Optional<StudentGroup> studentGroup = groupRepository.findById(id);
        if (studentGroup.isEmpty()) {
            log.error("student group with id: {} not found", id);
            throw new NotFoundException("student group not found");
        }
        else {
            return studentGroup.get();
        }
    }
}
