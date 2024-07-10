package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Task;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByDiscipline_id(Integer id);

    @Query(value = "SELECT * FROM lessons WHERE complexity BETWEEN ?1 AND ?2",
            nativeQuery = true)
    Optional<List<Task>> findByComplexityInRange(BigDecimal minCost, BigDecimal maxCost);

    @Query(value = "SELECT * " +
            "FROM tasks AS t " +
            "JOIN lesson_tasks AS lt ON lt.task_id = t.id " +
            "WHERE lt.lesson_id = ?1 ",
            nativeQuery = true)
    Optional<List<Task>> findByLessonId(Long lessonId);

    @Query(value = "SELECT t.* " +
            "FROM tasks AS t " +
            "JOIN task_game_methods AS tm ON tm.task_id = t.id " +
            "WHERE tm.game_method_id = ?1 ",
            nativeQuery = true)
    Optional<Task> findByGameMethodId(Long methodId);

    @Query(value = "SELECT t.* " +
            "FROM tasks AS t " +
            "JOIN course_tasks AS ct ON ct.task_id = t.id " +
            "WHERE ct.course_id = ?1 ",
            nativeQuery = true)
    Optional<List<Task>> findByCourseId(Long courseId);
}
