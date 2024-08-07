package ru.nsu.GameStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}