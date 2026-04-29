package br.edu.ufscar.backend.mealsfinder.services.user;

import br.edu.ufscar.backend.mealsfinder.dtos.user.UserStatsDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Follow;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import br.edu.ufscar.backend.mealsfinder.models.key.FollowId;
import br.edu.ufscar.backend.mealsfinder.repositories.FollowRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.PostRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository,
                       FollowRepository followRepository,
                       PostRepository postRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.postRepository = postRepository;
    }

    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado: " + id));
    }

    public UserStatsDTO getStats(String userId, String viewerId) {
        getById(userId);

        long followers = followRepository.countByIdFollowingId(userId);
        long following = followRepository.countByIdFollowerId(userId);
        long posts = postRepository.countByUserId(userId);

        boolean followedByViewer = false;
        if (viewerId != null && !viewerId.isBlank() && !viewerId.equals(userId)) {
            followedByViewer = followRepository.existsById(new FollowId(viewerId, userId));
        }

        return new UserStatsDTO(followers, following, posts, followedByViewer);
    }

    @Transactional
    public void follow(String followerId, String followingId) {
        if (followerId == null || followerId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "followerId é obrigatório");
        }
        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível seguir a si mesmo");
        }

        User follower = getById(followerId);
        if (!(follower instanceof Client)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Apenas clientes podem seguir outros usuários");
        }

        User following = getById(followingId);

        FollowId id = new FollowId(followerId, followingId);
        if (followRepository.existsById(id)) {
            return;
        }

        followRepository.save(new Follow((Client) follower, following));
    }

    @Transactional
    public void unfollow(String followerId, String followingId) {
        if (followerId == null || followerId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "followerId é obrigatório");
        }

        FollowId id = new FollowId(followerId, followingId);
        if (followRepository.existsById(id)) {
            followRepository.deleteById(id);
        }
    }
}
