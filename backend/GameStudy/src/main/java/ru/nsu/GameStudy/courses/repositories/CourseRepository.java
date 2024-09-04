package ru.nsu.GameStudy.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.courses.models.Course;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCreator_id(Long id);

    List<Course> findByLessons_Discipline_idAndTasks_Discipline_id(Long lessonDiscId, Long TaskDiscId);

    List<Course> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);

    List<Course> findByRatingBetween(Double minRate, Double maxRate);

    List<Course> findByTags_id(Long tagId);
}
