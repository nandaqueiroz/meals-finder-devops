package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.image.ImageUpsertRequestDTO;
import br.edu.ufscar.backend.mealsfinder.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ImageResponseDTO getById(@PathVariable String id) {
        return imageService.getDto(id);
    }

    @PutMapping("/{id}")
    public ImageResponseDTO update(@PathVariable String id, @RequestBody ImageUpsertRequestDTO dto) {
        return imageService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        imageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
