package ru.nsu.GameStudy.gamification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.gamification.models.GameMethod;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameMethodRepository extends JpaRepository<GameMethod, Long> {
    List<GameMethod> findByType(String type);

    Optional<GameMethod> findByTitle(String title);

    Boolean existsByTitle(String title);

    List<GameMethod> findByTags_id(Long tagId);
}
