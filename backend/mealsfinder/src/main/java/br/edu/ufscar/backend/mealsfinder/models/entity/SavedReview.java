package br.edu.ufscar.backend.mealsfinder.models.entity;

import br.edu.ufscar.backend.mealsfinder.models.key.SavedReviewId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "saved_reviews")
public class SavedReview {

    @EmbeddedId
    private SavedReviewId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    public SavedReview() {
    }

    public SavedReview(Client client, Review review) {
        this.client = client;
        this.review = review;
        this.id = new SavedReviewId(client.getId(), review.getId());
    }

    public SavedReviewId getId() {
        return id;
    }

    public void setId(SavedReviewId id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedReview that = (SavedReview) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
