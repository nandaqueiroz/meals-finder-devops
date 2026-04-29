package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;

public interface IRegistrationService {
    Client registerClient(ClientRegisterDTO clientRegisterDTO);
    Establishment registerEstablishment(EstablishmentRegisterDTO establishmentRegisterDTO);
}
