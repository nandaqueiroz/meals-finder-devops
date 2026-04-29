package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.establishment.EstablishmentResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.establishment.EstablishmentUpdateDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;
import br.edu.ufscar.backend.mealsfinder.repositories.EstablishmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findAllDtos() {
        return establishmentRepository.findAll().stream()
                .map(EstablishmentResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public Establishment findById(String id) {
        return establishmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estabelecimento com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public EstablishmentResponseDTO getDto(String id) {
        return new EstablishmentResponseDTO(findById(id));
    }

    @Transactional
    public EstablishmentResponseDTO updateEstablishmentProfile(String id, EstablishmentUpdateDTO updateDTO) {
        Establishment establishment = findById(id);
        updateDTO.applyTo(establishment);
        return new EstablishmentResponseDTO(establishmentRepository.save(establishment));
    }

    @Transactional
    public void deleteById(String id) {
        if (!establishmentRepository.existsById(id)) {
            throw new NoSuchElementException("Estabelecimento com ID " + id + " não encontrado.");
        }
        establishmentRepository.deleteById(id);
    }

    @Transactional
    public EstablishmentResponseDTO analyzeEstablishment(String id, AnalysisResult result) {
        Establishment establishment = findById(id);
        establishment.handleAnalysis(result);
        establishmentRepository.save(establishment);
        return new EstablishmentResponseDTO(establishment);
    }
}
