package ru.nsu.GameStudy.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.users.models.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserData_id(Long id);

    Boolean existsByUserData_id(Long id);

    List<Teacher> findByOrganisation(String organisation);

    List<Teacher> findByQualification(String qualification);

    List<Teacher> findByCourses_id(Long courseId);
}
