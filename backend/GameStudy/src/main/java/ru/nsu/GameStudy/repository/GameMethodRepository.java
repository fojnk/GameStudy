package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.GameMethod;
import ru.nsu.GameStudy.models.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameMethodRepository extends JpaRepository<GameMethod, Integer> {
    Optional<List<GameMethod>> findByType(String type);

    @Query(value = "SELECT m.id, m.title, m.type " +
            "FROM game_methods AS m " +
            "JOIN task_game_methods AS tm ON tm.game_method_id = m.id " +
            "WHERE tm.task_id = ?1 ",
            nativeQuery = true)
    Optional<GameMethod> findByTaskId(Long taskId);

    @Query(value = "SELECT m.* " +
            "FROM game_methods AS m " +
            "JOIN game_method_tags AS mt ON mt.game_method_id = m.id " +
            "WHERE mt.tag_id = ?1 ",
            nativeQuery = true)
    Optional<List<GameMethod>> findByTagId(Integer id);

    @Query(value = "SELECT m.* " +
            "FROM game_methods AS m " +
            "JOIN game_method_tags AS mt ON mt.game_method_id = m.id " +
            "JOIN tags AS t ON t.id = mt.tag_id " +
            "WHERE t.title = ?1 ",
            nativeQuery = true)
    Optional<List<GameMethod>> findByTagTitle(String title);
}
