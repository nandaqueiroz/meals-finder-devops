package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.CredentialsDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.LoginResponseDTO;

public interface ILoginService {
    LoginResponseDTO login(CredentialsDTO credentials);
}
