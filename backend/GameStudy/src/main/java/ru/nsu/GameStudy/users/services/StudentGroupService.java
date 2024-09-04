package ru.nsu.GameStudy.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.mappers.StudentGroupMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.users.dto.StudentGroupDTO;
import ru.nsu.GameStudy.users.dto.StudentGroupRequest;
import ru.nsu.GameStudy.users.models.StudentGroup;
import ru.nsu.GameStudy.users.repositories.StudentGroupRepository;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.models.Student;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentGroupService {
    private final StudentGroupRepository groupRepository;
    private final StudentGroupMapper groupMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentGroupDTO getStudentGroupDTO(Long id) {
        return groupMapper.toDTO(getStudentGroup(id));
    }

    public List<StudentGroupDTO> getAllStudentGroups() {
        log.info("GET all group");
        return groupRepository.findAll().stream().map(groupMapper::toDTO).toList();
    }

    @Transactional
    public void deleteStudentGroup(Long id) {
        log.info("DELETE group with id: {}", id);
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        } else {
            log.error("group with id: {} not found", id);
            throw new NotFoundException("group not found");
        }
    }

    @Transactional
    public StudentGroupDTO updateStudentGroup(Long id, StudentGroupRequest request) {
        log.info("UPDATE group with id: {}, request: {" +
                        "title: {}}", id,
                request.getTitle());
        StudentGroup group = getStudentGroup(id);

        groupMapper.updateGroupFromDTO(group, request);

        return save(group);
    }

    @Transactional
    public StudentGroupDTO createStudentGroup(StudentGroupRequest request) {
        log.info("CREATE group, request: {" +
                        "title: {}}",
                request.getTitle());

        StudentGroup group = StudentGroup.builder().build();
        groupMapper.updateGroupFromDTO(group, request);

        return save(group);
    }

    public List<StudentDTO> getStudentsInGroup(Long id) {
        log.info("GET students in group {}", id);
        StudentGroup group = getStudentGroup(id);
        return group.getStudents().stream().map(studentMapper::toDTO).toList();
    }

    @Transactional
    public StudentGroupDTO addStudentToGroup(Long id, Long studentId) {
        log.info("ADD student {} to group {}", studentId, id);
        StudentGroup group = getStudentGroup(id);
        group.getStudents().add(studentService.getStudent(studentId));
        return save(group);
    }

    @Transactional
    public StudentGroupDTO removeStudentFromGroup(Long id, Long studentId) {
        log.info("REMOVE student {} from group {}", id, studentId);
        StudentGroup group = getStudentGroup(id);
        group.getStudents().remove(studentService.getStudent(studentId));
        return save(group);
    }

    @Transactional
    public List<StudentGroupDTO> getGroupsForStudent(Long studentId) {
        log.info("GET student {} groups", studentId);
        Student student = studentService.getStudent(studentId);
        return student.getGroups().stream().map(groupMapper::toDTO).toList();
    }

    @Transactional
    public List<StudentGroupDTO> getGroupsByCreator(Long creatorId) {
        log.info("GET groups created by user {}", creatorId);
        return groupRepository
                .findByCreator_id(creatorId)
                .stream()
                .map(groupMapper::toDTO)
                .toList();
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