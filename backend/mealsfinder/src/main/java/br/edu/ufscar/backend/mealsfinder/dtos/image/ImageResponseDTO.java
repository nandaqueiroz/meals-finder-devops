package br.edu.ufscar.backend.mealsfinder.dtos.image;

import br.edu.ufscar.backend.mealsfinder.models.entity.Image;
import br.edu.ufscar.backend.mealsfinder.models.enums.ImageType;

import java.time.LocalDateTime;

public class ImageResponseDTO {
    private String id;
    private String url;
    private ImageType imageType;
    private LocalDateTime createdAt;
    private String establishmentId;
    private String reviewId;

    public ImageResponseDTO() {
    }

    public ImageResponseDTO(Image image) {
        this.id = image.getId();
        this.url = image.getUrl();
        this.imageType = image.getImageType();
        this.createdAt = image.getCreatedAt();
        if (image.getEstablishment() != null) {
            this.establishmentId = image.getEstablishment().getId();
        }
        if (image.getReview() != null) {
            this.reviewId = image.getReview().getId();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(String establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
