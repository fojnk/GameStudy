package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Student;
import ru.nsu.GameStudy.models.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserData_id(Long id);

    Optional<List<Teacher>> findByOrganisation(String organisation);

    Optional<List<Teacher>> findByQualification(String qualification);

    @Query(value = "SELECT t.* FROM teachers AS t " +
            "JOIN course_teachers AS ct ON ct.teacher_id = t.id " +
            "WHERE ct.course_id = ?1 ",
            nativeQuery = true)
    Optional<List<Teacher>> findByCourseId(Long courseId);
}
