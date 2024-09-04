package ru.nsu.GameStudy.student_activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.student_activity.models.TaskActivity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long> {
    List<TaskActivity> findByExperienceBetween(Integer minExp, Integer maxExp);

    List<TaskActivity> findByTask_id(Long taskId);

    List<TaskActivity> findByStudent_id(Long studentId);

    Optional<TaskActivity> findByTask_idAndStudent_id(Long taskId, Long studentId);
}
