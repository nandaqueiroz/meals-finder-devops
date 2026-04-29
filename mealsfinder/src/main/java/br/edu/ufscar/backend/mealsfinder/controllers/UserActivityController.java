package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.models.UserActivity;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import br.edu.ufscar.backend.mealsfinder.models.enums.ActivityType;
import br.edu.ufscar.backend.mealsfinder.models.enums.EntityType;
import br.edu.ufscar.backend.mealsfinder.repositories.UserActivityRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-activity")
public class UserActivityController {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/like/{entityType}/{entityId}")
    public ResponseEntity<UserActivity> likeEntity(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String userId) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserActivity activity = new UserActivity();
        activity.setId(UUID.randomUUID().toString());
        activity.setUser(userOpt.get());
        activity.setActionType(ActivityType.LIKE_POST);
        activity.setEntityType(EntityType.valueOf(entityType.toUpperCase()));
        activity.setEntityId(entityId);

        userActivityRepository.save(activity);
        return ResponseEntity.ok(activity);
    }

    @PostMapping("/save/{entityType}/{entityId}")
    public ResponseEntity<UserActivity> saveEntity(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String userId) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserActivity activity = new UserActivity();
        activity.setId(UUID.randomUUID().toString());
        activity.setUser(userOpt.get());
        activity.setActionType(ActivityType.SAVE_POST);
        activity.setEntityType(EntityType.valueOf(entityType.toUpperCase()));
        activity.setEntityId(entityId);

        userActivityRepository.save(activity);
        return ResponseEntity.ok(activity);
    }

    @DeleteMapping("/like/{entityType}/{entityId}")
    @Transactional
    public ResponseEntity<Void> unlikeEntity(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String userId) {

        userActivityRepository.deleteByUserIdAndEntityIdAndActionType(
            userId, entityId, ActivityType.LIKE_POST);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/save/{entityType}/{entityId}")
    @Transactional
    public ResponseEntity<Void> unsaveEntity(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String userId) {

        userActivityRepository.deleteByUserIdAndEntityIdAndActionType(
            userId, entityId, ActivityType.SAVE_POST);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/likes/{entityType}/{entityId}")
    public ResponseEntity<List<UserActivity>> getLikes(
            @PathVariable String entityType,
            @PathVariable String entityId) {

        List<UserActivity> likes = userActivityRepository.findByEntityIdAndEntityTypeAndActionType(
            entityId, EntityType.valueOf(entityType.toUpperCase()), ActivityType.LIKE_POST);

        return ResponseEntity.ok(likes);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<UserActivity>> getUserLikes(@PathVariable String userId) {
        List<UserActivity> likes = userActivityRepository.findByUserIdAndActionType(userId, ActivityType.LIKE_POST);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/user/{userId}/saves")
    public ResponseEntity<List<UserActivity>> getUserSaves(@PathVariable String userId) {
        List<UserActivity> saves = userActivityRepository.findByUserIdAndActionType(userId, ActivityType.SAVE_POST);
        return ResponseEntity.ok(saves);
    }
}
