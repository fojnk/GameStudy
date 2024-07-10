package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Lesson;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<List<Lesson>> findByDiscipline_id(Integer id);

    @Query(value = "SELECT * FROM lessons WHERE complexity BETWEEN ?1 AND ?2",
            nativeQuery = true)
    Optional<List<Lesson>> findByComplexityInRange(BigDecimal minCost, BigDecimal maxCost);

    @Query(value = "SELECT l.id, l.title, l.description, " +
            "l.discipline_id, l.experience, l.complexity " +
            "FROM lessons AS l " +
            "JOIN course_lessons AS cl ON cl.lesson_id = l.id " +
            "WHERE cl.course_id = ?1 ",
            nativeQuery = true)
    Optional<List<Lesson>> findByCourseId(Long courseId);
}
