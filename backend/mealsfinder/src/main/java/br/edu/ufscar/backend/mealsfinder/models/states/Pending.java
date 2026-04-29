package br.edu.ufscar.backend.mealsfinder.models.states;

import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;

import java.time.LocalDate;

public final class Pending implements EstablishmentState {

    private static final Pending INSTANCE = new Pending();

    private Pending() {
    }

    public static Pending getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleAnalysis(Establishment establishment, AnalysisResult result) {
        if (result == AnalysisResult.ACCEPTED) {
            establishment.setVisible(true);
            establishment.setState(Accepted.getInstance());
        } else {
            registerRejection(establishment);
        }
    }

    static void registerRejection(Establishment establishment) {
        int next = establishment.getRejectionsCount() + 1;
        establishment.setRejectionsCount(next);
        establishment.setRejectionDate(LocalDate.now());
        if (next >= 2) {
            establishment.setState(Banned.getInstance());
        } else {
            establishment.setState(Rejected.getInstance());
        }
    }
}
