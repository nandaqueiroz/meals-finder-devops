package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageUpsertRequestDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.entity.Image;
import br.edu.ufscar.backend.mealsfinder.models.entity.Review;
import br.edu.ufscar.backend.mealsfinder.models.enums.ImageType;
import br.edu.ufscar.backend.mealsfinder.repositories.EstablishmentRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.ImageRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageService {

    private static final Set<ImageType> ESTABLISHMENT_TYPES = EnumSet.of(ImageType.ESTABLISHMENT_PICTURE, ImageType.MENU);
    private static final Set<ImageType> REVIEW_TYPES = EnumSet.of(ImageType.POST, ImageType.MENU);

    private final ImageRepository imageRepository;
    private final EstablishmentRepository establishmentRepository;
    private final ReviewRepository reviewRepository;

    public ImageService(
            ImageRepository imageRepository,
            EstablishmentRepository establishmentRepository,
            ReviewRepository reviewRepository) {
        this.imageRepository = imageRepository;
        this.establishmentRepository = establishmentRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Image findById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Imagem com ID " + id + " não encontrada."));
    }

    @Transactional(readOnly = true)
    public ImageResponseDTO getDto(String id) {
        return new ImageResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ImageResponseDTO> listDtosByEstablishmentId(String establishmentId) {
        if (!establishmentRepository.existsById(establishmentId)) {
            throw new NoSuchElementException("Estabelecimento com ID " + establishmentId + " não encontrado.");
        }
        return imageRepository.findByEstablishmentId(establishmentId).stream().map(ImageResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ImageResponseDTO> listDtosByReviewId(String reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new NoSuchElementException("Avaliação com ID " + reviewId + " não encontrada.");
        }
        return imageRepository.findByReviewId(reviewId).stream().map(ImageResponseDTO::new).toList();
    }

    @Transactional
    public ImageResponseDTO createForEstablishment(String establishmentId, ImageUpsertRequestDTO dto) {
        validateUpsert(dto);
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new NoSuchElementException("Estabelecimento com ID " + establishmentId + " não encontrado."));
        if (!ESTABLISHMENT_TYPES.contains(dto.getImageType())) {
            throw new IllegalArgumentException("Para imagens de estabelecimento use imageType MENU ou ESTABLISHMENT_PICTURE.");
        }
        Image image = new Image();
        image.setId(UUID.randomUUID().toString());
        image.setEstablishment(establishment);
        image.setUrl(dto.getUrl().trim());
        image.setImageType(dto.getImageType());
        return new ImageResponseDTO(imageRepository.save(image));
    }

    @Transactional
    public ImageResponseDTO createForReview(String reviewId, ImageUpsertRequestDTO dto) {
        validateUpsert(dto);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + reviewId + " não encontrada."));
        if (!REVIEW_TYPES.contains(dto.getImageType())) {
            throw new IllegalArgumentException("Para imagens de avaliação use imageType POST ou MENU.");
        }
        Image image = new Image();
        image.setId(UUID.randomUUID().toString());
        image.setReview(review);
        image.setUrl(dto.getUrl().trim());
        image.setImageType(dto.getImageType());
        return new ImageResponseDTO(imageRepository.save(image));
    }

    @Transactional
    public ImageResponseDTO update(String imageId, ImageUpsertRequestDTO dto) {
        Image image = findById(imageId);
        if (dto.getUrl() != null && !dto.getUrl().isBlank()) {
            image.setUrl(dto.getUrl().trim());
        }
        if (dto.getImageType() != null) {
            if (image.getEstablishment() != null && !ESTABLISHMENT_TYPES.contains(dto.getImageType())) {
                throw new IllegalArgumentException("Tipo de imagem inválido para estabelecimento.");
            }
            if (image.getReview() != null && !REVIEW_TYPES.contains(dto.getImageType())) {
                throw new IllegalArgumentException("Tipo de imagem inválido para avaliação.");
            }
            image.setImageType(dto.getImageType());
        }
        return new ImageResponseDTO(imageRepository.save(image));
    }

    @Transactional
    public void deleteById(String imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new NoSuchElementException("Imagem com ID " + imageId + " não encontrada.");
        }
        imageRepository.deleteById(imageId);
    }

    private static void validateUpsert(ImageUpsertRequestDTO dto) {
        if (dto == null || dto.getUrl() == null || dto.getUrl().isBlank()) {
            throw new IllegalArgumentException("URL da imagem é obrigatória.");
        }
        if (dto.getImageType() == null) {
            throw new IllegalArgumentException("imageType é obrigatório.");
        }
    }
}
