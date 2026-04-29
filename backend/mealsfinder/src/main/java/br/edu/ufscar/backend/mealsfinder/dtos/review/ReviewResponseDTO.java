package br.edu.ufscar.backend.mealsfinder.dtos.review;

import br.edu.ufscar.backend.mealsfinder.models.entity.Review;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ReviewResponseDTO {
    private String id;
    private String userId;
    private String username;
    private String description;
    private LocalDateTime createdAt;
    private String reviewedEstablishmentId;
    private Double priceRate;
    private Double foodRate;
    private Double establishmentRate;
    private Double serviceRate;
    private Double deliveryRate;
    private boolean deliveryReview;
    private Set<FoodTag> foodTags = new HashSet<>();
    private Set<ServiceTag> serviceTags = new HashSet<>();
    private Set<EnvironmentTag> environmentTags = new HashSet<>();

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        if (review.getUser() != null) {
            this.userId = review.getUser().getId();
            this.username = review.getUser().getUsername();
        }
        this.description = review.getDescription();
        this.createdAt = review.getCreatedAt();
        if (review.getReviewedEstablishment() != null) {
            this.reviewedEstablishmentId = review.getReviewedEstablishment().getId();
        }
        this.priceRate = review.getPriceRate();
        this.foodRate = review.getFoodRate();
        this.establishmentRate = review.getEstablishmentRate();
        this.serviceRate = review.getServiceRate();
        this.deliveryRate = review.getDeliveryRate();
        this.deliveryReview = review.isDeliveryReview();
        if (review.getFoodTags() != null) {
            this.foodTags = new HashSet<>(review.getFoodTags());
        }
        if (review.getServiceTags() != null) {
            this.serviceTags = new HashSet<>(review.getServiceTags());
        }
        if (review.getEnvironmentTags() != null) {
            this.environmentTags = new HashSet<>(review.getEnvironmentTags());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReviewedEstablishmentId() {
        return reviewedEstablishmentId;
    }

    public void setReviewedEstablishmentId(String reviewedEstablishmentId) {
        this.reviewedEstablishmentId = reviewedEstablishmentId;
    }

    public Double getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public Double getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(Double foodRate) {
        this.foodRate = foodRate;
    }

    public Double getEstablishmentRate() {
        return establishmentRate;
    }

    public void setEstablishmentRate(Double establishmentRate) {
        this.establishmentRate = establishmentRate;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Double getDeliveryRate() {
        return deliveryRate;
    }

    public void setDeliveryRate(Double deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public boolean isDeliveryReview() {
        return deliveryReview;
    }

    public void setDeliveryReview(boolean deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public Set<FoodTag> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(Set<FoodTag> foodTags) {
        this.foodTags = foodTags != null ? foodTags : new HashSet<>();
    }

    public Set<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags != null ? serviceTags : new HashSet<>();
    }

    public Set<EnvironmentTag> getEnvironmentTags() {
        return environmentTags;
    }

    public void setEnvironmentTags(Set<EnvironmentTag> environmentTags) {
        this.environmentTags = environmentTags != null ? environmentTags : new HashSet<>();
    }
}
