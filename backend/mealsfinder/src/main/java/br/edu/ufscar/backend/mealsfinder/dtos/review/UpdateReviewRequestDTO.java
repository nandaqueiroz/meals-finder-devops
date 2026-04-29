package br.edu.ufscar.backend.mealsfinder.dtos.review;

import java.util.Set;

/** Partial update: only non-null fields are applied (tags: null = no change, empty set = clear). */
public class UpdateReviewRequestDTO {
    private String description;
    private Set<String> foodTags;
    private Set<String> serviceTags;
    private Set<String> environmentTags;
    private String reviewedEstablishmentId;
    private Double priceRate;
    private Double foodRate;
    private Double establishmentRate;
    private Double serviceRate;
    private Double deliveryRate;
    private Boolean deliveryReview;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(Set<String> foodTags) {
        this.foodTags = foodTags;
    }

    public Set<String> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(Set<String> serviceTags) {
        this.serviceTags = serviceTags;
    }

    public Set<String> getEnvironmentTags() {
        return environmentTags;
    }

    public void setEnvironmentTags(Set<String> environmentTags) {
        this.environmentTags = environmentTags;
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

    public Boolean getDeliveryReview() {
        return deliveryReview;
    }

    public void setDeliveryReview(Boolean deliveryReview) {
        this.deliveryReview = deliveryReview;
    }
}
