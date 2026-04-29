package br.edu.ufscar.backend.mealsfinder.dtos.follow;

import br.edu.ufscar.backend.mealsfinder.models.entity.Follow;

public class FollowResponseDTO {
    private String followerId;
    private String followerUsername;
    private String followingId;
    private String followingUsername;

    public FollowResponseDTO() {
    }

    public FollowResponseDTO(Follow follow) {
        this.followerId = follow.getFollower().getId();
        this.followerUsername = follow.getFollower().getUsername();
        this.followingId = follow.getFollowing().getId();
        this.followingUsername = follow.getFollowing().getUsername();
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }
}
