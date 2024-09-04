package ru.nsu.GameStudy.gamification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.gamification.models.Achievement;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findByRequiredExp(Integer exp);

    Optional<Achievement> findByTitle(String title);

    Boolean existsByTitle(String title);
}
