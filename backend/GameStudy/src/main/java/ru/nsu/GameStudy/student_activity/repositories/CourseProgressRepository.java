package ru.nsu.GameStudy.student_activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.student_activity.models.CourseProgress;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
    List<CourseProgress> findByCourse_id(Long id);

    List<CourseProgress> findByStudent_id(Long id);

    Optional<CourseProgress> findByCourse_idAndStudent_id(Long courseId, Long studentId);
}
