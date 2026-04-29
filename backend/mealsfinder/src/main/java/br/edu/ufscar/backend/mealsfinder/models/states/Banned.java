package br.edu.ufscar.backend.mealsfinder.models.states;

import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;

public final class Banned implements EstablishmentState {

    private static final Banned INSTANCE = new Banned();

    private Banned() {
    }

    public static Banned getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleAnalysis(Establishment establishment, AnalysisResult result) {
        // Estado final: análises não alteram o cadastro.
    }
}
