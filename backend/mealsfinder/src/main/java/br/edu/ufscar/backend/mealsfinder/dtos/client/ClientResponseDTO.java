package br.edu.ufscar.backend.mealsfinder.dtos.client;

import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ClientResponseDTO {
    private String id;
    private String email;
    private String username;
    private String phoneNumber;
    private String profilePictureUrl;
    private String bio;
    private LocalDateTime accountCreationDate;
    private Set<FoodTag> likedFoodTags = new HashSet<>();
    private Set<FoodTag> dislikedFoodTags = new HashSet<>();
    private long followingCount;
    private long followedCount;
    private long reviewsPostedCount;
    private long reviewsSavedCount;

    public ClientResponseDTO() {
    }

    public ClientResponseDTO(Client client) {
        this.id = client.getId();
        this.email = client.getEmail();
        this.username = client.getUsername();
        this.phoneNumber = client.getPhoneNumber();
        this.profilePictureUrl = client.getProfilePictureUrl();
        this.bio = client.getBio();
        this.accountCreationDate = client.getAccountCreationDate();
        if (client.getLikedFoodTags() != null) {
            this.likedFoodTags = new HashSet<>(client.getLikedFoodTags());
        }
        if (client.getDislikedFoodTags() != null) {
            this.dislikedFoodTags = new HashSet<>(client.getDislikedFoodTags());
        }
        this.followingCount = client.getFollowingCount();
        this.followedCount = client.getFollowedCount();
        this.reviewsPostedCount = client.getReviewsPostedCount();
        this.reviewsSavedCount = client.getReviewsSavedCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public Set<FoodTag> getLikedFoodTags() {
        return likedFoodTags;
    }

    public void setLikedFoodTags(Set<FoodTag> likedFoodTags) {
        this.likedFoodTags = likedFoodTags != null ? likedFoodTags : new HashSet<>();
    }

    public Set<FoodTag> getDislikedFoodTags() {
        return dislikedFoodTags;
    }

    public void setDislikedFoodTags(Set<FoodTag> dislikedFoodTags) {
        this.dislikedFoodTags = dislikedFoodTags != null ? dislikedFoodTags : new HashSet<>();
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(long followedCount) {
        this.followedCount = followedCount;
    }

    public long getReviewsPostedCount() {
        return reviewsPostedCount;
    }

    public void setReviewsPostedCount(long reviewsPostedCount) {
        this.reviewsPostedCount = reviewsPostedCount;
    }

    public long getReviewsSavedCount() {
        return reviewsSavedCount;
    }

    public void setReviewsSavedCount(long reviewsSavedCount) {
        this.reviewsSavedCount = reviewsSavedCount;
    }
}
