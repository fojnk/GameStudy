package ru.nsu.GameStudy.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.authentication.models.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

        @Query(value = "SELECT t.* FROM tokens t INNER JOIN users u ON t.user_id = u.id" +
            " WHERE t.user_id = ?1 AND t.is_logged_out = false", nativeQuery = true)
        List<Token> findAllAccessTokensByUser(Long userId);

        Optional<Token> findByAccessToken(String token);

        Optional<Token > findByRefreshToken(String token);
}