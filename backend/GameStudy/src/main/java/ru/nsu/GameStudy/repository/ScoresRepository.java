package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Scores;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoresRepository extends JpaRepository<Scores, Long> {
    Optional<List<Scores>> findByStudent_id(Long id);

    Optional<List<Scores>> findByCourse_id(Long id);

    Optional<List<Scores>> findByStudent_idAndCourse_id(Long studentId, Long courseId);
}
