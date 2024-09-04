package ru.nsu.GameStudy.course_attributes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;

import java.util.Optional;

@Repository
public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {
    Optional<CourseTag> findByTitle(String title);

    Boolean existsByTitle(String title);
}
