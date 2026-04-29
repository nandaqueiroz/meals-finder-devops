package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.CredentialsDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.LoginResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.client.ClientResponseDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.establishment.EstablishmentResponseDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.services.LoginService;
import br.edu.ufscar.backend.mealsfinder.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final LoginService loginService;
    private final RegistrationService registrationService;

    public AuthenticationController(LoginService loginService, RegistrationService registrationService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CredentialsDTO credentials) {
        return ResponseEntity.ok(loginService.login(credentials));
    }

    @PostMapping("/register/client")
    public ResponseEntity<ClientResponseDTO> registerClient(@RequestBody ClientRegisterDTO dto) {
        Client client = registrationService.registerClient(dto);
        return ResponseEntity.ok(new ClientResponseDTO(client));
    }

    @PostMapping("/register/establishment")
    public ResponseEntity<EstablishmentResponseDTO> registerEstablishment(@RequestBody EstablishmentRegisterDTO dto) {
        Establishment establishment = registrationService.registerEstablishment(dto);
        return ResponseEntity.ok(new EstablishmentResponseDTO(establishment));
    }
}


