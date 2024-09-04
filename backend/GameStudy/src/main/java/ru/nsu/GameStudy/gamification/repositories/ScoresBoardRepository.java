package ru.nsu.GameStudy.gamification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.gamification.models.ScoresBoard;
import ru.nsu.GameStudy.users.models.Student;

import java.util.List;

@Repository
public interface ScoresBoardRepository extends JpaRepository<ScoresBoard, Long> {
    List<ScoresBoard> findByCourse_id(Long courseId);

    List<ScoresBoard> findByCourse_Students_id(Long studentId);
}
