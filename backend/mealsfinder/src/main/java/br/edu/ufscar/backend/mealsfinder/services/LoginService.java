package br.edu.ufscar.backend.mealsfinder.services;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.CredentialsDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.LoginResponseDTO;
import br.edu.ufscar.backend.mealsfinder.errors.InvalidCredentialsException;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;
import br.edu.ufscar.backend.mealsfinder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService implements ILoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDTO login(CredentialsDTO credentials) {
        if (credentials == null) {
            throw new IllegalArgumentException("Credenciais não informadas.");
        }
        if (credentials.getEmail() == null || credentials.getEmail().isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        if (credentials.getPassword() == null) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }

        User user = userRepository.findByEmail(credentials.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        if (!Objects.equals(credentials.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return LoginResponseDTO.fromUser(user);
    }
}
