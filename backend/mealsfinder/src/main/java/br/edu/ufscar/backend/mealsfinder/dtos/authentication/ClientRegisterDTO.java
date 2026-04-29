package br.edu.ufscar.backend.mealsfinder.dtos.authentication;

import com.fasterxml.jackson.annotation.JsonAlias;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;

import java.util.HashSet;
import java.util.Set;

public class ClientRegisterDTO {
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private String profilePicUrl;
    private String bio;
    private Set<FoodTag> likedFoodTags = new HashSet<>();
    private Set<FoodTag> dislikedFoodTags = new HashSet<>();

    public ClientRegisterDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<FoodTag> getLikedFoodTags() {
        return likedFoodTags;
    }

    @JsonAlias("likes")
    public void setLikedFoodTags(Set<FoodTag> likedFoodTags) {
        this.likedFoodTags = likedFoodTags != null ? likedFoodTags : new HashSet<>();
    }

    public Set<FoodTag> getDislikedFoodTags() {
        return dislikedFoodTags;
    }

    @JsonAlias("dislikes")
    public void setDislikedFoodTags(Set<FoodTag> dislikedFoodTags) {
        this.dislikedFoodTags = dislikedFoodTags != null ? dislikedFoodTags : new HashSet<>();
    }
}
