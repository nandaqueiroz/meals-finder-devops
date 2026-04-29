package br.edu.ufscar.backend.mealsfinder.dtos.client;

public class FollowUserRequestDTO {
    private String followingUserId;

    public String getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(String followingUserId) {
        this.followingUserId = followingUserId;
    }
}
