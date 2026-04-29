package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.comment.CommentResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.comment.CreateCommentRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.comment.UpdateCommentRequestDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Comment;
import br.edu.ufscar.backend.mealsfinder.models.entity.CommentLike;
import br.edu.ufscar.backend.mealsfinder.models.entity.Review;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import br.edu.ufscar.backend.mealsfinder.models.key.CommentLikeId;
import br.edu.ufscar.backend.mealsfinder.repositories.CommentLikeRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.CommentRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.ReviewRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            CommentLikeRepository commentLikeRepository,
            ReviewRepository reviewRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findDtosByReviewId(String reviewId) {
        return findByReviewId(reviewId).stream().map(CommentResponseDTO::new).toList();
    }

    public List<Comment> findByReviewId(String reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new NoSuchElementException("Avaliação com ID " + reviewId + " não encontrada.");
        }
        return commentRepository.findByReviewId(reviewId);
    }

    @Transactional(readOnly = true)
    public CommentResponseDTO getDto(String id) {
        return new CommentResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findAllDtos() {
        return commentRepository.findAll().stream().map(CommentResponseDTO::new).toList();
    }

    public Comment findById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comentário com ID " + id + " não encontrado."));
    }

    @Transactional
    public CommentResponseDTO createComment(String reviewId, CreateCommentRequestDTO dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + reviewId + " não encontrada."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + dto.getUserId() + " não encontrado."));

        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Descrição do comentário é obrigatória.");
        }

        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setReview(review);
        comment.setUser(user);
        comment.setDescription(dto.getDescription().trim());

        if (dto.getParentCommentId() != null && !dto.getParentCommentId().isBlank()) {
            Comment parent = findById(dto.getParentCommentId());
            if (!parent.getReview().getId().equals(reviewId)) {
                throw new IllegalArgumentException("Comentário pai pertence a outra avaliação.");
            }
            comment.setParentComment(parent);
        }

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDTO(saved);
    }

    @Transactional
    public CommentResponseDTO updateComment(String commentId, String userId, UpdateCommentRequestDTO dto) {
        Comment comment = findById(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Somente o autor pode editar este comentário.");
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Descrição do comentário é obrigatória.");
        }
        comment.setDescription(dto.getDescription().trim());
        return new CommentResponseDTO(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(String commentId, String requestUserId) {
        Comment comment = findById(commentId);
        if (!comment.getUser().getId().equals(requestUserId)) {
            throw new IllegalStateException("Somente o autor pode remover este comentário.");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public void likeComment(String commentId, String userId) {
        Comment comment = findById(commentId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + userId + " não encontrado."));
        CommentLikeId id = new CommentLikeId(userId, commentId);
        if (commentLikeRepository.existsById(id)) {
            return;
        }
        commentLikeRepository.save(new CommentLike(user, comment));
    }

    @Transactional
    public void unlikeComment(String commentId, String userId) {
        CommentLikeId id = new CommentLikeId(userId, commentId);
        if (commentLikeRepository.existsById(id)) {
            commentLikeRepository.deleteById(id);
        }
    }
}
