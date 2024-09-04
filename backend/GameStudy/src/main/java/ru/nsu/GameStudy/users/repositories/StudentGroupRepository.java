package ru.nsu.GameStudy.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.users.models.StudentGroup;

import java.util.List;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    List<StudentGroup> findByCourses_id(Long courseId);

    List<StudentGroup> findByCreator_id(Long creatorId);
}
