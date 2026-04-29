package br.edu.ufscar.backend.mealsfinder.models.enums;

public enum UserType {
    CLIENT("client"),
    ESTABLISHMENT("establishment");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
