package br.edu.ufscar.backend.mealsfinder.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract class Content {
    private UUID id;
    private String text;
    private List<UUID> likes;
    private UUID creatorId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;

    public Content() {
        this.id = UUID.randomUUID();
        this.likes = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
        this.lastModifiedDate = this.creationDate;
    }

    public Content(UUID creatorId, String text) {
        this();
        this.creatorId = creatorId;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public List<UUID> getLikes() {
        return likes;
    }

    public void setLikes(List<UUID> likes) {
        this.likes = likes;
    }
    
    public int getLikeCount() {
        return likes.size();
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }
    
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
    
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public boolean addLike(UUID userId) {
        if (!likes.contains(userId)) {
            likes.add(userId);
            return true;
        }
        return false;
    }

    public boolean removeLike(UUID userId) {
        return likes.remove(userId);
    }

    public boolean isLikedBy(UUID userId) {
        return likes.contains(userId);
    }

    public abstract String getContentType();
    
    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", likes=" + getLikeCount() +
                ", created=" + creationDate +
                '}';
    }
}
