package br.edu.ufscar.backend.mealsfinder.dtos.client;

import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;

import java.util.HashSet;
import java.util.Set;

/** Partial update: only non-null fields are applied. */
public class ClientUpdateDTO {
    private String bio;
    private String profilePictureUrl;
    private String phoneNumber;
    private Set<FoodTag> likedFoodTags;
    private Set<FoodTag> dislikedFoodTags;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<FoodTag> getLikedFoodTags() {
        return likedFoodTags;
    }

    public void setLikedFoodTags(Set<FoodTag> likedFoodTags) {
        this.likedFoodTags = likedFoodTags;
    }

    public Set<FoodTag> getDislikedFoodTags() {
        return dislikedFoodTags;
    }

    public void setDislikedFoodTags(Set<FoodTag> dislikedFoodTags) {
        this.dislikedFoodTags = dislikedFoodTags;
    }

    public void applyTo(Client client) {
        if (bio != null) {
            client.setBio(bio);
        }
        if (profilePictureUrl != null) {
            client.setProfilePictureUrl(profilePictureUrl);
        }
        if (phoneNumber != null) {
            client.setPhoneNumber(phoneNumber);
        }
        if (likedFoodTags != null) {
            client.setLikedFoodTags(new HashSet<>(likedFoodTags));
        }
        if (dislikedFoodTags != null) {
            client.setDislikedFoodTags(new HashSet<>(dislikedFoodTags));
        }
    }
}
