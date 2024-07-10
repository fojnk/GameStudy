package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Tag;
import ru.nsu.GameStudy.models.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT t.* " +
            "FROM tags AS t " +
            "JOIN course_tags AS ct ON ct.tag_id = t.id " +
            "WHERE ct.course_id = ?1 ",
            nativeQuery = true)
    Optional<List<Tag>> findByCourseId(Long id);

    @Query(value = "SELECT t.* " +
            "FROM tags AS t " +
            "JOIN game_method_tags AS mt ON mt.tag_id = t.id " +
            "WHERE mt.game_method_id = ?1",
            nativeQuery = true)
    Optional<List<Tag>> findByGameMethodId(Integer id);
}
