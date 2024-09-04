package ru.nsu.GameStudy.course_attributes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.course_attributes.models.GameTag;

import java.util.Optional;

@Repository
public interface GameTagRepository extends JpaRepository<GameTag, Long> {
    Optional<GameTag> findByTitle(String title);

    Boolean existsByTitle(String title);
}
