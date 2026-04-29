package br.edu.ufscar.backend.mealsfinder.dtos.comment;

import br.edu.ufscar.backend.mealsfinder.models.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private String id;
    private String reviewId;
    private String userId;
    private String username;
    private String parentCommentId;
    private String description;
    private LocalDateTime createdAt;
    private long likesCount;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.reviewId = comment.getReview().getId();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getId();
        }
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
        this.likesCount = comment.getLikesCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
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

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
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

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }
}
