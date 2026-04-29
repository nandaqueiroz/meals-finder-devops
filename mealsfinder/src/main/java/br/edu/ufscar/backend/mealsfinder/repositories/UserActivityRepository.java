package br.edu.ufscar.backend.mealsfinder.repositories;

import br.edu.ufscar.backend.mealsfinder.models.UserActivity;
import br.edu.ufscar.backend.mealsfinder.models.enums.ActivityType;
import br.edu.ufscar.backend.mealsfinder.models.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, String> {

    List<UserActivity> findByUserIdAndActionType(String userId, ActivityType actionType);

    List<UserActivity> findByEntityIdAndEntityTypeAndActionType(String entityId, EntityType entityType, ActivityType actionType);

    @Modifying
    @Query("DELETE FROM UserActivity ua WHERE ua.user.id = :userId AND ua.entityId = :entityId AND ua.actionType = :actionType")
    void deleteByUserIdAndEntityIdAndActionType(@Param("userId") String userId, @Param("entityId") String entityId, @Param("actionType") ActivityType actionType);
}