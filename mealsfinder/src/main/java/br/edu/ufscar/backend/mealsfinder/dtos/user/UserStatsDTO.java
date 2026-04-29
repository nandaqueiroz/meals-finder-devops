package br.edu.ufscar.backend.mealsfinder.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserStatsDTO {
    private long followers;
    private long following;
    private long posts;

    @JsonProperty("isFollowing")
    private boolean followedByViewer;

    public UserStatsDTO() {
    }

    public UserStatsDTO(long followers, long following, long posts, boolean followedByViewer) {
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.followedByViewer = followedByViewer;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public boolean isFollowedByViewer() {
        return followedByViewer;
    }

    public void setFollowedByViewer(boolean followedByViewer) {
        this.followedByViewer = followedByViewer;
    }
}
