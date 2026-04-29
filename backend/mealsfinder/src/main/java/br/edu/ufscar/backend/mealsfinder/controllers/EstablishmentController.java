package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.establishment.EstablishmentResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.establishment.EstablishmentUpdateDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageUpsertRequestDTO;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;
import br.edu.ufscar.backend.mealsfinder.services.EstablishmentService;
import br.edu.ufscar.backend.mealsfinder.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;
    private final ImageService imageService;

    public EstablishmentController(EstablishmentService establishmentService, ImageService imageService) {
        this.establishmentService = establishmentService;
        this.imageService = imageService;
    }

    @GetMapping
    public List<EstablishmentResponseDTO> getAllEstablishments() {
        return establishmentService.findAllDtos();
    }

    @GetMapping("/{id}")
    public EstablishmentResponseDTO getEstablishmentById(@PathVariable String id) {
        return establishmentService.getDto(id);
    }

    @PutMapping("/{id}")
    public EstablishmentResponseDTO updateEstablishment(
            @PathVariable String id,
            @RequestBody EstablishmentUpdateDTO updateDTO) {
        return establishmentService.updateEstablishmentProfile(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable String id) {
        establishmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/images")
    public List<ImageResponseDTO> listImages(@PathVariable String id) {
        return imageService.listDtosByEstablishmentId(id);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ImageResponseDTO> addImage(
            @PathVariable String id,
            @RequestBody ImageUpsertRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.createForEstablishment(id, dto));
    }

    @PatchMapping("/analyze/{id}")
    public ResponseEntity<EstablishmentResponseDTO> analyzeEstablishment(
            @PathVariable String id,
            @RequestParam("result") AnalysisResult result) {
        return ResponseEntity.ok(establishmentService.analyzeEstablishment(id, result));
    }
}
