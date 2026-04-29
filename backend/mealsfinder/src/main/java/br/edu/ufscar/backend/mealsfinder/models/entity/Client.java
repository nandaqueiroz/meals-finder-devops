package br.edu.ufscar.backend.mealsfinder.models.entity;

import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@DiscriminatorValue("client")
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "client_liked_food_tags",
            joinColumns = @JoinColumn(name = "client_id", nullable = false)
    )
    @Column(name = "food_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<FoodTag> likedFoodTags = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "client_disliked_food_tags",
            joinColumns = @JoinColumn(name = "client_id", nullable = false)
    )
    @Column(name = "food_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<FoodTag> dislikedFoodTags = new HashSet<>();

    @Formula("(select coalesce((select count(*) from follows f where f.follower_id = id), 0))")
    private long followingCount;

    @Formula("(select coalesce((select count(*) from follows f2 where f2.following_id = id), 0))")
    private long followedCount;

    @Formula("(select coalesce((select count(*) from reviews r where r.user_id = id), 0))")
    private long reviewsPostedCount;

    @Formula("(select coalesce((select count(*) from saved_reviews s where s.user_id = id), 0))")
    private long reviewsSavedCount;

    public Client() {
        super();
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

    public long getFollowingCount() {
        return followingCount;
    }

    public long getFollowedCount() {
        return followedCount;
    }

    public long getReviewsPostedCount() {
        return reviewsPostedCount;
    }

    public long getReviewsSavedCount() {
        return reviewsSavedCount;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}
