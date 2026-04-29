package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findByUserId(String userId);

    long countByUserId(String userId);
}

