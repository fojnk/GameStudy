package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Achievements;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievements, Long> {
    @Query(value = "SELECT a.* " +
            "FROM achievements AS a " +
            "JOIN student_achievements AS sa ON sa.achievement_id = a.id " +
            "WHERE sa.student_id = ?1 ",
            nativeQuery = true)
    Optional<List<Achievements>> findByStudentId(Long studentId);

    Optional<List<Achievements>> findByRequiredExp(Integer exp);
}
