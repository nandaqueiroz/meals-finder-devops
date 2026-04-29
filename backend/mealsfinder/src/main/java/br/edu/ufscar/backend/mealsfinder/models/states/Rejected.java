package br.edu.ufscar.backend.mealsfinder.models.states;

import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;

public final class Rejected implements EstablishmentState {

    private static final Rejected INSTANCE = new Rejected();

    private Rejected() {
    }

    public static Rejected getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleAnalysis(Establishment establishment, AnalysisResult result) {
        if (result == AnalysisResult.ACCEPTED) {
            establishment.setVisible(true);
            establishment.setState(Accepted.getInstance());
        } else {
            Pending.registerRejection(establishment);
        }
    }
}
