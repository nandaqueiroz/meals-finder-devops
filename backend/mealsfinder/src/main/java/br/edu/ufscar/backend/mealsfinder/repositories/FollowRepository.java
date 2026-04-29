package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.entity.Follow;
import br.edu.ufscar.backend.mealsfinder.models.key.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    List<Follow> findByIdFollowingId(String followingId);

    List<Follow> findByIdFollowerId(String followerId);
}
