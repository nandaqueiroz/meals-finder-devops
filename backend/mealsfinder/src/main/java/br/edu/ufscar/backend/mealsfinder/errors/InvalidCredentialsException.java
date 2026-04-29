package br.edu.ufscar.backend.mealsfinder.errors;

/**
 * E-mail inexistente ou senha incorreta (não detalha qual falhou).
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas.");
    }
}
