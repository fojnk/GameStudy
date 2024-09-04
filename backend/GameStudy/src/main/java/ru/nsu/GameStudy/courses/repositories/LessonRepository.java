package ru.nsu.GameStudy.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.models.Task;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByDiscipline_id(Integer id);

    List<Lesson> findByComplexityBetween(BigDecimal min, BigDecimal max);

    List<Lesson> findByGameMethods_id(Long methodId);

    List<Lesson> findByTasks_id(Long taskId);
}
