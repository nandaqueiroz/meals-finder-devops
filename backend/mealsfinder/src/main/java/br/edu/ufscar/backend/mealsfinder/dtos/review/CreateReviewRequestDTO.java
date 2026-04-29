package br.edu.ufscar.backend.mealsfinder.dtos.review;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Set;

public class CreateReviewRequestDTO {
    private String description;

    private Set<String> foodTags;
    private Set<String> serviceTags;
    private Set<String> environmentTags;

    private String establishmentId;
    private String clientId;
    private Double priceRate;
    private Double foodRate;
    private Double establishmentRate;
    private Double serviceRate;
    private Double deliveryRate;
    private boolean deliveryReview;

    public CreateReviewRequestDTO() {
    }

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

    public String getEstablishmentId() {
        return establishmentId;
    }

    @JsonAlias("reviewedEstablishmentId")
    public void setEstablishmentId(String establishmentId) {
        this.establishmentId = establishmentId;
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

    public String getClientId() {
        return clientId;
    }

    @JsonAlias("userId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}