package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.follow.FollowResponseDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Follow;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import br.edu.ufscar.backend.mealsfinder.models.key.FollowId;
import br.edu.ufscar.backend.mealsfinder.repositories.ClientRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.FollowRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, ClientRepository clientRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Follow> listFollowing(String clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new NoSuchElementException("Cliente com ID " + clientId + " não encontrado.");
        }
        return followRepository.findByIdFollowerId(clientId);
    }

    @Transactional(readOnly = true)
    public List<FollowResponseDTO> listFollowingDtos(String clientId) {
        return listFollowing(clientId).stream().map(FollowResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<Follow> listFollowers(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("Usuário com ID " + userId + " não encontrado.");
        }
        return followRepository.findByIdFollowingId(userId);
    }

    @Transactional(readOnly = true)
    public List<FollowResponseDTO> listFollowersDtos(String userId) {
        return listFollowers(userId).stream().map(FollowResponseDTO::new).toList();
    }

    @Transactional
    public FollowResponseDTO followDto(String followerClientId, String followingUserId) {
        return new FollowResponseDTO(follow(followerClientId, followingUserId));
    }

    @Transactional
    public Follow follow(String followerClientId, String followingUserId) {
        Client follower = clientRepository.findById(followerClientId)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + followerClientId + " não encontrado."));
        User following = userRepository.findById(followingUserId)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + followingUserId + " não encontrado."));

        if (follower.getId().equals(following.getId())) {
            throw new IllegalArgumentException("Não é possível seguir a si mesmo.");
        }

        FollowId id = new FollowId(followerClientId, followingUserId);
        if (followRepository.existsById(id)) {
            return followRepository.findById(id).orElseThrow();
        }

        return followRepository.save(new Follow(follower, following));
    }

    @Transactional
    public void unfollow(String followerClientId, String followingUserId) {
        FollowId id = new FollowId(followerClientId, followingUserId);
        if (!followRepository.existsById(id)) {
            throw new NoSuchElementException("Relação de seguir não encontrada.");
        }
        followRepository.deleteById(id);
    }
}
