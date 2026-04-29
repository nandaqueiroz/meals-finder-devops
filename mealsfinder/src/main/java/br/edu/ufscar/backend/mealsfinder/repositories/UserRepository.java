package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT COALESCE(MAX(CAST(id AS INTEGER)), 0) FROM users", nativeQuery = true)
    long findMaxNumericId();
}