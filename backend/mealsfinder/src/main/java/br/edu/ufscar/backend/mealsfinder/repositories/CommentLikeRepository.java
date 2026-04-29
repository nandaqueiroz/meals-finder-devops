package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.entity.CommentLike;
import br.edu.ufscar.backend.mealsfinder.models.key.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
}
