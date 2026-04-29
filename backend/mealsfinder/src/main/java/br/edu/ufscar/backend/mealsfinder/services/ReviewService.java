package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.review.CreateReviewRequestDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.ReviewResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.review.UpdateReviewRequestDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.entity.Review;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ReviewSortOrder;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import br.edu.ufscar.backend.mealsfinder.models.states.Accepted;
import br.edu.ufscar.backend.mealsfinder.repositories.ClientRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.EstablishmentRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EstablishmentRepository establishmentRepository;
    private final ClientRepository clientRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            EstablishmentRepository establishmentRepository,
            ClientRepository clientRepository) {
        this.reviewRepository = reviewRepository;
        this.establishmentRepository = establishmentRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Avaliação com ID " + id + " não encontrada."));
    }

    @Transactional(readOnly = true)
    public ReviewResponseDTO getDto(String id) {
        return new ReviewResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findAllDtos() {
        return findAll().stream().map(ReviewResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<Review> findByEstablishmentId(String establishmentId) {
        return reviewRepository.findByReviewedEstablishmentId(establishmentId);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findDtosByEstablishmentId(String establishmentId) {
        return findByEstablishmentId(establishmentId).stream().map(ReviewResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<Review> findByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findDtosByUserId(String userId) {
        return findByUserId(userId).stream().map(ReviewResponseDTO::new).toList();
    }

    @Transactional
    public ReviewResponseDTO createReviewDto(CreateReviewRequestDTO dto) {
        return new ReviewResponseDTO(createReview(dto));
    }

    @Transactional
    public Review createReview(CreateReviewRequestDTO dto) {
        Establishment establishment = establishmentRepository.findById(dto.getEstablishmentId())
                .orElseThrow(() -> new NoSuchElementException("Estabelecimento com ID " + dto.getEstablishmentId() + " não encontrado."));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + dto.getClientId() + " não encontrado."));

        if (!(establishment.getState() instanceof Accepted)) {
            throw new IllegalStateException("Não é possível avaliar um estabelecimento que não está com o status 'ACEITO'.");
        }

        String reviewId = UUID.randomUUID().toString();
        Review review = new Review();
        review.setId(reviewId);
        review.setCreatedAt(LocalDateTime.now());
        review.setDescription(dto.getDescription());
        review.setUser(client);
        review.setReviewedEstablishment(establishment);
        review.setPriceRate(dto.getPriceRate());
        review.setFoodRate(dto.getFoodRate());
        review.setEstablishmentRate(dto.getEstablishmentRate());
        review.setServiceRate(dto.getServiceRate());
        review.setDeliveryRate(dto.getDeliveryRate());
        review.setDeliveryReview(dto.isDeliveryReview());
        review.setFoodTags(parseFoodTags(dto.getFoodTags()));
        review.setServiceTags(parseServiceTags(dto.getServiceTags()));
        review.setEnvironmentTags(parseEnvironmentTags(dto.getEnvironmentTags()));

        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(String reviewId, String requestUserId) {
        Review review = findById(reviewId);
        if (!review.getUser().getId().equals(requestUserId)) {
            throw new IllegalStateException("Somente o autor pode remover esta avaliação.");
        }
        reviewRepository.delete(review);
    }

    @Transactional
    public ReviewResponseDTO updateReviewDto(String reviewId, String userId, UpdateReviewRequestDTO dto) {
        Review review = findById(reviewId);
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Somente o autor pode editar esta avaliação.");
        }
        if (dto.getDescription() != null) {
            review.setDescription(dto.getDescription());
        }
        if (dto.getFoodTags() != null) {
            review.setFoodTags(parseFoodTags(dto.getFoodTags()));
        }
        if (dto.getServiceTags() != null) {
            review.setServiceTags(parseServiceTags(dto.getServiceTags()));
        }
        if (dto.getEnvironmentTags() != null) {
            review.setEnvironmentTags(parseEnvironmentTags(dto.getEnvironmentTags()));
        }
        if (dto.getReviewedEstablishmentId() != null) {
            Establishment est = establishmentRepository.findById(dto.getReviewedEstablishmentId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Estabelecimento com ID " + dto.getReviewedEstablishmentId() + " não encontrado."));
            if (!(est.getState() instanceof Accepted)) {
                throw new IllegalStateException("Estabelecimento alvo precisa estar com status ACEITO.");
            }
            review.setReviewedEstablishment(est);
        }
        if (dto.getPriceRate() != null) {
            review.setPriceRate(dto.getPriceRate());
        }
        if (dto.getFoodRate() != null) {
            review.setFoodRate(dto.getFoodRate());
        }
        if (dto.getEstablishmentRate() != null) {
            review.setEstablishmentRate(dto.getEstablishmentRate());
        }
        if (dto.getServiceRate() != null) {
            review.setServiceRate(dto.getServiceRate());
        }
        if (dto.getDeliveryRate() != null) {
            review.setDeliveryRate(dto.getDeliveryRate());
        }
        if (dto.getDeliveryReview() != null) {
            review.setDeliveryReview(dto.getDeliveryReview());
        }
        return new ReviewResponseDTO(reviewRepository.save(review));
    }

    /**
     * Filtros do tipo &quot;Filtrar&quot;: entre categorias (comida, ambiente, serviço) vale E;
     * dentro da mesma categoria, basta a avaliação ou o estabelecimento ter uma das tags (OU).
     * Cidade/UF restringem pelo endereço do estabelecimento avaliado.
     */
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findFilteredDtos(
            Set<FoodTag> foodTags,
            Set<EnvironmentTag> environmentTags,
            Set<ServiceTag> serviceTags,
            String city,
            String state) {
        List<Review> list = new ArrayList<>(filterDiscoveryReviews(
                foodTags != null ? foodTags : Set.of(),
                environmentTags != null ? environmentTags : Set.of(),
                serviceTags != null ? serviceTags : Set.of(),
                city,
                state));
        list.sort(Comparator.comparing(Review::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        return list.stream().map(ReviewResponseDTO::new).toList();
    }

    /**
     * Ordenação &quot;Ordenar por&quot;; aceita os mesmos filtros opcionais de {@link #findFilteredDtos}.
     */
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findOrderedDtos(
            ReviewSortOrder sort,
            Set<FoodTag> foodTags,
            Set<EnvironmentTag> environmentTags,
            Set<ServiceTag> serviceTags,
            String city,
            String state) {
        ReviewSortOrder effective = sort != null ? sort : ReviewSortOrder.RELEVANCE;
        List<Review> list = new ArrayList<>(filterDiscoveryReviews(
                foodTags != null ? foodTags : Set.of(),
                environmentTags != null ? environmentTags : Set.of(),
                serviceTags != null ? serviceTags : Set.of(),
                city,
                state));
        sortReviews(list, effective);
        return list.stream().map(ReviewResponseDTO::new).toList();
    }

    private List<Review> filterDiscoveryReviews(
            Set<FoodTag> foodTags,
            Set<EnvironmentTag> environmentTags,
            Set<ServiceTag> serviceTags,
            String city,
            String state) {
        return reviewRepository.findByReviewedEstablishmentIsNotNull().stream()
                .filter(r -> matchesLocation(r, city, state))
                .filter(r -> matchesFoodTags(r, foodTags))
                .filter(r -> matchesEnvironmentTags(r, environmentTags))
                .filter(r -> matchesServiceTags(r, serviceTags))
                .toList();
    }

    private static boolean matchesLocation(Review review, String city, String state) {
        Establishment establishment = review.getReviewedEstablishment();
        if (establishment == null) {
            return false;
        }
        var address = establishment.getAddress();
        if (city != null && !city.isBlank()) {
            if (address == null || address.getCity() == null
                    || !city.trim().equalsIgnoreCase(address.getCity().trim())) {
                return false;
            }
        }
        if (state != null && !state.isBlank()) {
            if (address == null || address.getState() == null
                    || !state.trim().equalsIgnoreCase(address.getState().trim())) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesFoodTags(Review review, Set<FoodTag> selected) {
        if (selected.isEmpty()) {
            return true;
        }
        Establishment establishment = review.getReviewedEstablishment();
        Set<FoodTag> union = new HashSet<>(review.getFoodTags());
        if (establishment != null && establishment.getFoodTags() != null) {
            union.addAll(establishment.getFoodTags());
        }
        return selected.stream().anyMatch(union::contains);
    }

    private static boolean matchesEnvironmentTags(Review review, Set<EnvironmentTag> selected) {
        if (selected.isEmpty()) {
            return true;
        }
        Establishment establishment = review.getReviewedEstablishment();
        Set<EnvironmentTag> union = new HashSet<>(review.getEnvironmentTags());
        if (establishment != null && establishment.getEnvironmentTags() != null) {
            union.addAll(establishment.getEnvironmentTags());
        }
        return selected.stream().anyMatch(union::contains);
    }

    private static boolean matchesServiceTags(Review review, Set<ServiceTag> selected) {
        if (selected.isEmpty()) {
            return true;
        }
        Establishment establishment = review.getReviewedEstablishment();
        Set<ServiceTag> union = new HashSet<>(review.getServiceTags());
        if (establishment != null && establishment.getServiceTags() != null) {
            union.addAll(establishment.getServiceTags());
        }
        return selected.stream().anyMatch(union::contains);
    }

    private static void sortReviews(List<Review> reviews, ReviewSortOrder sort) {
        Comparator<Review> comparator = switch (sort) {
            case RELEVANCE -> Comparator
                    .comparingDouble(ReviewService::overallRatingOrNegInfinity)
                    .reversed()
                    .thenComparing(
                            Comparator.comparing(
                                    Review::getCreatedAt,
                                    Comparator.nullsLast(Comparator.naturalOrder()))
                                    .reversed());
            case PRICE_ASC -> Comparator.comparing(
                    Review::getPriceRate, Comparator.nullsLast(Comparator.naturalOrder()));
            case PRICE_DESC -> Comparator.comparing(
                    Review::getPriceRate, Comparator.nullsLast(Comparator.reverseOrder()));
            case RATING_DESC -> Comparator.comparingDouble(ReviewService::overallRatingOrNegInfinity).reversed();
            case RATING_ASC -> Comparator.comparingDouble(ReviewService::overallRatingOrPosInfinity);
        };
        reviews.sort(comparator);
    }

    /** Média das notas numéricas da avaliação (preço, comida, estabelecimento, serviço); ignora nulos. */
    private static Double overallRating(Review review) {
        double sum = 0;
        int n = 0;
        if (review.getPriceRate() != null) {
            sum += review.getPriceRate();
            n++;
        }
        if (review.getFoodRate() != null) {
            sum += review.getFoodRate();
            n++;
        }
        if (review.getEstablishmentRate() != null) {
            sum += review.getEstablishmentRate();
            n++;
        }
        if (review.getServiceRate() != null) {
            sum += review.getServiceRate();
            n++;
        }
        if (n == 0) {
            return null;
        }
        return sum / n;
    }

    private static double overallRatingOrNegInfinity(Review review) {
        Double v = overallRating(review);
        return v != null ? v : Double.NEGATIVE_INFINITY;
    }

    private static double overallRatingOrPosInfinity(Review review) {
        Double v = overallRating(review);
        return v != null ? v : Double.POSITIVE_INFINITY;
    }

    private static Set<FoodTag> parseFoodTags(Set<String> names) {
        if (names == null || names.isEmpty()) {
            return new HashSet<>();
        }
        Set<FoodTag> out = new HashSet<>();
        for (String n : names) {
            if (n != null && !n.isBlank()) {
                try {
                    out.add(FoodTag.valueOf(n.trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Tag de comida inválida: " + n);
                }
            }
        }
        return out;
    }

    private static Set<ServiceTag> parseServiceTags(Set<String> names) {
        if (names == null || names.isEmpty()) {
            return new HashSet<>();
        }
        Set<ServiceTag> out = new HashSet<>();
        for (String n : names) {
            if (n != null && !n.isBlank()) {
                try {
                    out.add(ServiceTag.valueOf(n.trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Tag de serviço inválida: " + n);
                }
            }
        }
        return out;
    }

    private static Set<EnvironmentTag> parseEnvironmentTags(Set<String> names) {
        if (names == null || names.isEmpty()) {
            return new HashSet<>();
        }
        Set<EnvironmentTag> out = new HashSet<>();
        for (String n : names) {
            if (n != null && !n.isBlank()) {
                try {
                    out.add(EnvironmentTag.valueOf(n.trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Tag de ambiente inválida: " + n);
                }
            }
        }
        return out;
    }
}
