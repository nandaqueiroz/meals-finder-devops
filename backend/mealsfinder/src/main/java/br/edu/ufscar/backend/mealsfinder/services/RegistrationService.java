package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.models.UserFactory;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.repositories.ClientRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.EstablishmentRepository;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements IRegistrationService {

    private final UserFactory userFactory;
    private final ClientRepository clientRepository;
    private final EstablishmentRepository establishmentRepository;
    private final UserRepository userRepository;

    public RegistrationService(
            UserFactory userFactory,
            ClientRepository clientRepository,
            EstablishmentRepository establishmentRepository,
            UserRepository userRepository) {
        this.userFactory = userFactory;
        this.clientRepository = clientRepository;
        this.establishmentRepository = establishmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Client registerClient(ClientRegisterDTO clientRegisterDTO) {
        validateUniqueUserFields(
                clientRegisterDTO.getEmail(),
                clientRegisterDTO.getUsername(),
                clientRegisterDTO.getPhoneNumber());

        Client newClient = userFactory.createClient(clientRegisterDTO);
        return clientRepository.save(newClient);
    }

    @Override
    @Transactional
    public Establishment registerEstablishment(EstablishmentRegisterDTO establishmentRegisterDTO) {
        validateUniqueUserFields(
                establishmentRegisterDTO.getEmail(),
                establishmentRegisterDTO.getUsername(),
                establishmentRegisterDTO.getPhoneNumber());

        if (establishmentRepository.existsByCnpj(establishmentRegisterDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        Establishment newEstablishment = userFactory.createEstablishment(establishmentRegisterDTO);
        return establishmentRepository.save(newEstablishment);
    }

    private void validateUniqueUserFields(String email, String username, String phoneNumber) {
        if (email != null && !email.isBlank() && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        if (username != null && !username.isBlank() && userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Nome de usuário já está em uso.");
        }
        if (phoneNumber != null && !phoneNumber.isBlank() && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Telefone já cadastrado.");
        }
    }
}
