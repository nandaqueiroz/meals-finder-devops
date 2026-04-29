package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.user.UserPublicDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.user.UserStatsDTO;
import br.edu.ufscar.backend.mealsfinder.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPublicDTO> getUser(@PathVariable String id) {
        return ResponseEntity.ok(UserPublicDTO.fromUser(userService.getById(id)));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<UserStatsDTO> getStats(
            @PathVariable String id,
            @RequestParam(value = "viewerId", required = false) String viewerId) {
        return ResponseEntity.ok(userService.getStats(id, viewerId));
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<Void> follow(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        userService.follow(body.get("followerId"), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/follow")
    public ResponseEntity<Void> unfollow(
            @PathVariable String id,
            @RequestParam("followerId") String followerId) {
        userService.unfollow(followerId, id);
        return ResponseEntity.noContent().build();
    }
}
