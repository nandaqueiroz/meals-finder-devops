package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.review.SavedReviewResponseDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Review;
import br.edu.ufscar.backend.mealsfinder.models.entity.SavedReview;
import br.edu.ufscar.backend.mealsfinder.models.key.SavedReviewId;
import br.edu.ufscar.backend.mealsfinder.repositories.ClientRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.ReviewRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.SavedReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SavedReviewService {

    private final SavedReviewRepository savedReviewRepository;
    private final ClientRepository clientRepository;
    private final ReviewRepository reviewRepository;

    public SavedReviewService(
            SavedReviewRepository savedReviewRepository,
            ClientRepository clientRepository,
            ReviewRepository reviewRepository) {
        this.savedReviewRepository = savedReviewRepository;
        this.clientRepository = clientRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public List<SavedReview> listSavedForClient(String clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new NoSuchElementException("Cliente com ID " + clientId + " não encontrado.");
        }
        return savedReviewRepository.findByIdUserId(clientId);
    }

    @Transactional(readOnly = true)
    public List<SavedReviewResponseDTO> listSavedDtosForClient(String clientId) {
        return listSavedForClient(clientId).stream().map(SavedReviewResponseDTO::new).toList();
    }

    @Transactional
    public SavedReviewResponseDTO saveReviewDto(String clientId, String reviewId) {
        return new SavedReviewResponseDTO(saveReview(clientId, reviewId));
    }

    @Transactional
    public SavedReview saveReview(String clientId, String reviewId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + clientId + " não encontrado."));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + reviewId + " não encontrada."));

        SavedReviewId id = new SavedReviewId(clientId, reviewId);
        if (savedReviewRepository.existsById(id)) {
            return savedReviewRepository.findById(id).orElseThrow();
        }

        return savedReviewRepository.save(new SavedReview(client, review));
    }

    @Transactional
    public void unsaveReview(String clientId, String reviewId) {
        SavedReviewId id = new SavedReviewId(clientId, reviewId);
        if (!savedReviewRepository.existsById(id)) {
            throw new NoSuchElementException("Avaliação salva não encontrada.");
        }
        savedReviewRepository.deleteById(id);
    }
}
