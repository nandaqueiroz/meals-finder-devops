package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.client.ClientResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.client.ClientUpdateDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Client findById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO getDto(String id) {
        return new ClientResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAllDtos() {
        return clientRepository.findAll().stream().map(ClientResponseDTO::new).toList();
    }

    @Transactional
    public ClientResponseDTO update(String id, ClientUpdateDTO dto) {
        Client client = findById(id);
        dto.applyTo(client);
        return new ClientResponseDTO(clientRepository.save(client));
    }

    @Transactional
    public void deleteById(String id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente com ID " + id + " não encontrado.");
        }
        clientRepository.deleteById(id);
    }
}
