package ru.nsu.GameStudy.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.users.models.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserData_id(Long userId);

    Boolean existsByUserData_id(Long userId);

    List<Student> findByCourses_id(Long courseId);
}
