package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
}
