package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Scores;
import ru.nsu.GameStudy.models.Teacher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<List<Course>> findByCreator_id(Long id);

    @Query(value = "SELECT * FROM courses WHERE cost BETWEEN ?1 AND ?2", nativeQuery = true)
    Optional<List<Course>> findByCostInRange(BigDecimal minCost, BigDecimal maxCost);

    @Query(value = "SELECT * FROM courses WHERE rating BETWEEN ?1 AND ?2", nativeQuery = true)
    Optional<List<Course>> findByRatingInRange(Double minRate, Double maxRate);

    @Query(value = "SELECT c.* " +
            "FROM courses AS c " +
            "JOIN course_tags AS ct ON ct.course_id = c.id " +
            "WHERE ct.tag_id = ?1 ",
            nativeQuery = true)
    Optional<List<Course>> findByTagId(Integer id);

    @Query(value = "SELECT c.* " +
            "FROM courses AS c " +
            "JOIN course_tags AS ct ON ct.course_id = c.id " +
            "JOIN tags AS t ON t.id = ct.tag_id " +
            "WHERE t.title = ?1 ",
            nativeQuery = true)
    Optional<List<Course>> findByTagTitle(String title);

    @Query(value = "SELECT c.* FROM courses AS c " +
            "JOIN course_teachers AS ct ON ct.course_id = c.id " +
            "WHERE ct.teacher_id = ?1 ",
            nativeQuery = true)
    Optional<List<Course>> findByTeacherId(Long teacherId);

    @Query(value = "SELECT c.* FROM courses AS c " +
            "JOIN course_students AS cs ON cs.course_id = c.id " +
            "WHERE cs.student_id = ?1 ",
            nativeQuery = true)
    Optional<List<Course>> findByStudentId(Long studentId);
}
