package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.comment.CommentResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.comment.CreateCommentRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.comment.UpdateCommentRequestDTO;
import br.edu.ufscar.backend.mealsfinder.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<CommentResponseDTO> listAll() {
        return commentService.findAllDtos();
    }

    @GetMapping("/comments/{commentId}")
    public CommentResponseDTO getById(@PathVariable String commentId) {
        return commentService.getDto(commentId);
    }

    @GetMapping("/reviews/{reviewId}/comments")
    public List<CommentResponseDTO> listByReview(@PathVariable String reviewId) {
        return commentService.findDtosByReviewId(reviewId);
    }

    @PostMapping("/reviews/{reviewId}/comments")
    public ResponseEntity<CommentResponseDTO> create(
            @PathVariable String reviewId,
            @RequestBody CreateCommentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(reviewId, dto));
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDTO update(
            @PathVariable String commentId,
            @RequestParam String userId,
            @RequestBody UpdateCommentRequestDTO dto) {
        return commentService.updateComment(commentId, userId, dto);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable String commentId, @RequestParam String userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> like(@PathVariable String commentId, @RequestParam String userId) {
        commentService.likeComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> unlike(@PathVariable String commentId, @RequestParam String userId) {
        commentService.unlikeComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
