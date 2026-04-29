package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.entity.SavedReview;
import br.edu.ufscar.backend.mealsfinder.models.key.SavedReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedReviewRepository extends JpaRepository<SavedReview, SavedReviewId> {

    List<SavedReview> findByIdUserId(String userId);
}
