package br.edu.ufscar.backend.mealsfinder.models.states;

import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;

public final class Accepted implements EstablishmentState {

    private static final Accepted INSTANCE = new Accepted();

    private Accepted() {
    }

    public static Accepted getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleAnalysis(Establishment establishment, AnalysisResult result) {
        if (result == AnalysisResult.REJECTED) {
            establishment.setVisible(false);
            Pending.registerRejection(establishment);
        }
    }
}
