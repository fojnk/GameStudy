package ru.nsu.GameStudy.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.courses.models.Task;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDiscipline_id(Integer id);

    List<Task> findByComplexityBetween(BigDecimal minCost, BigDecimal maxCost);

    List<Task> findByGameMethods_id(Long methodId);
}
