package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageUpsertRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.CreateReviewRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.ReviewResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.UpdateReviewRequestDTO;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ReviewSortOrder;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import br.edu.ufscar.backend.mealsfinder.services.ImageService;
import br.edu.ufscar.backend.mealsfinder.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ImageService imageService;

    public ReviewController(ReviewService reviewService, ImageService imageService) {
        this.reviewService = reviewService;
        this.imageService = imageService;
    }

    @GetMapping
    public List<ReviewResponseDTO> getAll() {
        return reviewService.findAllDtos();
    }

    @GetMapping("/filter")
    public List<ReviewResponseDTO> getFilteredReviews(
            @RequestParam(required = false) Set<FoodTag> foodTags,
            @RequestParam(required = false) Set<EnvironmentTag> environmentTags,
            @RequestParam(required = false) Set<ServiceTag> serviceTags,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        return reviewService.findFilteredDtos(foodTags, environmentTags, serviceTags, city, state);
    }

    @GetMapping("/ordered")
    public List<ReviewResponseDTO> getOrderedReviews(
            @RequestParam(defaultValue = "RELEVANCE") ReviewSortOrder sort,
            @RequestParam(required = false) Set<FoodTag> foodTags,
            @RequestParam(required = false) Set<EnvironmentTag> environmentTags,
            @RequestParam(required = false) Set<ServiceTag> serviceTags,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        return reviewService.findOrderedDtos(sort, foodTags, environmentTags, serviceTags, city, state);
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getById(@PathVariable String id) {
        return reviewService.getDto(id);
    }

    @GetMapping("/establishment/{establishmentId}")
    public List<ReviewResponseDTO> listByEstablishment(@PathVariable String establishmentId) {
        return reviewService.findDtosByEstablishmentId(establishmentId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewResponseDTO> listByUser(@PathVariable String userId) {
        return reviewService.findDtosByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody CreateReviewRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReviewDto(dto));
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO update(
            @PathVariable String id,
            @RequestParam String userId,
            @RequestBody UpdateReviewRequestDTO dto) {
        return reviewService.updateReviewDto(id, userId, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @RequestParam String userId) {
        reviewService.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/images")
    public List<ImageResponseDTO> listImages(@PathVariable String id) {
        return imageService.listDtosByReviewId(id);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ImageResponseDTO> addImage(
            @PathVariable String id,
            @RequestBody ImageUpsertRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.createForReview(id, dto));
    }
}
