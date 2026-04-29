// Crie este novo serviço
package br.edu.ufscar.backend.mealsfinder.services.authentication;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.models.UserFactory;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.states.Pending;
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

    public RegistrationService(UserFactory userFactory,
                               ClientRepository clientRepository,
                               EstablishmentRepository establishmentRepository,
                               UserRepository userRepository) {
        this.userFactory = userFactory;
        this.clientRepository = clientRepository;
        this.establishmentRepository = establishmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gera o próximo ID numérico sequencial em formato String.
     * Synchronized para evitar colisões em cadastros concorrentes.
     */
    private synchronized String nextUserId() {
        return String.valueOf(userRepository.findMaxNumericId() + 1);
    }

    @Override
    @Transactional
    public Client registerClient(ClientRegisterDTO clientRegisterDTO) {
        Client newClient = userFactory.createClient(clientRegisterDTO);
        newClient.setId(nextUserId());
        return clientRepository.save(newClient);
    }

    @Override
    @Transactional
    public Establishment registerEstablishment(EstablishmentRegisterDTO establishmentRegisterDTO) {
        Establishment newEstablishment = userFactory.createEstablishment(establishmentRegisterDTO);
        newEstablishment.setId(nextUserId());
        return establishmentRepository.save(newEstablishment);
    }
}