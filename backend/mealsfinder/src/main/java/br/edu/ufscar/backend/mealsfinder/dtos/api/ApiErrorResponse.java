package br.edu.ufscar.backend.mealsfinder.dtos.api;

/**
 * Corpo padrão de erro para a API.
 */
public class ApiErrorResponse {

    private String message;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
