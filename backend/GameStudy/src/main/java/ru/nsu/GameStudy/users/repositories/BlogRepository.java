package ru.nsu.GameStudy.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.users.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}