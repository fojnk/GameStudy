package ru.nsu.GameStudy.student_activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.student_activity.models.LessonActivity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonActivityRepository extends JpaRepository<LessonActivity, Long> {
    List<LessonActivity> findByExperienceBetween(Integer minExp, Integer maxExp);

    List<LessonActivity> findByLesson_id(Long lessonId);

    List<LessonActivity> findByStudent_id(Long studentId);

    Optional<LessonActivity> findByLesson_idAndStudent_id(Long lessonId, Long studentId);
}
