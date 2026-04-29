package br.edu.ufscar.backend.mealsfinder.dtos.review;

import br.edu.ufscar.backend.mealsfinder.models.entity.SavedReview;

public class SavedReviewResponseDTO {
    private String clientId;
    private ReviewResponseDTO review;

    public SavedReviewResponseDTO() {
    }

    public SavedReviewResponseDTO(SavedReview savedReview) {
        this.clientId = savedReview.getClient().getId();
        this.review = new ReviewResponseDTO(savedReview.getReview());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ReviewResponseDTO getReview() {
        return review;
    }

    public void setReview(ReviewResponseDTO review) {
        this.review = review;
    }
}
