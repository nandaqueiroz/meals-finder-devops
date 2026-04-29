package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.client.ClientResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.client.ClientUpdateDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.client.FollowUserRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.client.SaveReviewRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.follow.FollowResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.ReviewResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.SavedReviewResponseDTO;
import br.edu.ufscar.backend.mealsfinder.services.ClientService;
import br.edu.ufscar.backend.mealsfinder.services.FollowService;
import br.edu.ufscar.backend.mealsfinder.services.ReviewService;
import br.edu.ufscar.backend.mealsfinder.services.SavedReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final SavedReviewService savedReviewService;
    private final ReviewService reviewService;
    private final FollowService followService;

    public ClientController(
            ClientService clientService,
            SavedReviewService savedReviewService,
            ReviewService reviewService,
            FollowService followService) {
        this.clientService = clientService;
        this.savedReviewService = savedReviewService;
        this.reviewService = reviewService;
        this.followService = followService;
    }

    @GetMapping
    public List<ClientResponseDTO> list() {
        return clientService.findAllDtos();
    }

    @GetMapping("/{id}")
    public ClientResponseDTO getById(@PathVariable String id) {
        return clientService.getDto(id);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO update(@PathVariable String id, @RequestBody ClientUpdateDTO dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}/saved-reviews")
    public List<SavedReviewResponseDTO> getAllSavedReviews(@PathVariable String clientId) {
        return savedReviewService.listSavedDtosForClient(clientId);
    }

    @GetMapping("/{clientId}/posted-reviews")
    public List<ReviewResponseDTO> getAllPostedReviews(@PathVariable String clientId) {
        clientService.findById(clientId);
        return reviewService.findDtosByUserId(clientId);
    }

    @PostMapping("/{clientId}/saved-reviews")
    public ResponseEntity<SavedReviewResponseDTO> saveReview(
            @PathVariable String clientId,
            @RequestBody SaveReviewRequestDTO body) {
        if (body.getReviewId() == null || body.getReviewId().isBlank()) {
            throw new IllegalArgumentException("reviewId é obrigatório.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                savedReviewService.saveReviewDto(clientId, body.getReviewId()));
    }

    @DeleteMapping("/{clientId}/saved-reviews/{reviewId}")
    public ResponseEntity<Void> unsaveReview(
            @PathVariable String clientId,
            @PathVariable String reviewId) {
        savedReviewService.unsaveReview(clientId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}/following")
    public List<FollowResponseDTO> listFollowing(@PathVariable String clientId) {
        return followService.listFollowingDtos(clientId);
    }

    @GetMapping("/{clientId}/followers")
    public List<FollowResponseDTO> listFollowers(@PathVariable String clientId) {
        return followService.listFollowersDtos(clientId);
    }

    @PostMapping("/{clientId}/following")
    public ResponseEntity<FollowResponseDTO> follow(
            @PathVariable String clientId,
            @RequestBody FollowUserRequestDTO body) {
        if (body.getFollowingUserId() == null || body.getFollowingUserId().isBlank()) {
            throw new IllegalArgumentException("followingUserId é obrigatório.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                followService.followDto(clientId, body.getFollowingUserId()));
    }

    @DeleteMapping("/{clientId}/following/{followingUserId}")
    public ResponseEntity<Void> unfollow(
            @PathVariable String clientId,
            @PathVariable String followingUserId) {
        followService.unfollow(clientId, followingUserId);
        return ResponseEntity.noContent().build();
    }
}
