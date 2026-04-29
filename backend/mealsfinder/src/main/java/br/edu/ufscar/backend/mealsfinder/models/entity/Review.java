package br.edu.ufscar.backend.mealsfinder.models.entity;

import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "review_food_tags", joinColumns = @JoinColumn(name = "review_id", nullable = false))
    @Column(name = "food_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<FoodTag> foodTags = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "review_service_tags", joinColumns = @JoinColumn(name = "review_id", nullable = false))
    @Column(name = "service_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<ServiceTag> serviceTags = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "review_environment_tags", joinColumns = @JoinColumn(name = "review_id", nullable = false))
    @Column(name = "environment_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<EnvironmentTag> environmentTags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_establishment_id")
    private Establishment reviewedEstablishment;

    @Column(name = "price_rate")
    private Double priceRate;

    @Column(name = "food_rate")
    private Double foodRate;

    @Column(name = "establishment_rate")
    private Double establishmentRate;

    @Column(name = "service_rate")
    private Double serviceRate;

    @Column(name = "delivery_rate")
    private Double deliveryRate;

    @Column(name = "is_delivery_review", nullable = false)
    private boolean deliveryReview;

    public Review() {
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<FoodTag> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(Set<FoodTag> foodTags) {
        this.foodTags = foodTags;
    }

    public Set<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags;
    }

    public Set<EnvironmentTag> getEnvironmentTags() {
        return environmentTags;
    }

    public void setEnvironmentTags(Set<EnvironmentTag> environmentTags) {
        this.environmentTags = environmentTags;
    }

    public Establishment getReviewedEstablishment() {
        return reviewedEstablishment;
    }

    public void setReviewedEstablishment(Establishment reviewedEstablishment) {
        this.reviewedEstablishment = reviewedEstablishment;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
