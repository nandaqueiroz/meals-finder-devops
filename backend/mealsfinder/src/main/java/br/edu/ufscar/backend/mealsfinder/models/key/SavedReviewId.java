package br.edu.ufscar.backend.mealsfinder.models.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SavedReviewId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "review_id")
    private String reviewId;

    public SavedReviewId() {
    }

    public SavedReviewId(String userId, String reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedReviewId that = (SavedReviewId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, reviewId);
    }
}
