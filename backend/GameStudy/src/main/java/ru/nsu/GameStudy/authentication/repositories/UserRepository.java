package ru.nsu.GameStudy.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.GameStudy.authentication.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndSurnameAndFathersName(
            String name, String Surname, String fathersName);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);
}