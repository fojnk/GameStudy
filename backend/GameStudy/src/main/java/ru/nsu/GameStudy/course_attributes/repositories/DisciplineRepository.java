package ru.nsu.GameStudy.course_attributes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.course_attributes.models.Discipline;

import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByTitle(String title);

    Boolean existsByTitle(String title);
}
