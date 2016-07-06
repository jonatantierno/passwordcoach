package es.jonatantierno.passwordcoach.domain.model.tips;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;

public class RandomTipSource implements TipSource {
    private final String[] adviceTitles;
    private final String[] adviceContents;
    private final String[] techniqueTitles;
    private final String[] techniqueContents;

    public RandomTipSource(String[] adviceTitles, String[] adviceContents, String[] techniqueTitles, String[] techniqueContents) {
        this.adviceTitles = adviceTitles;
        this.adviceContents = adviceContents;
        this.techniqueTitles = techniqueTitles;
        this.techniqueContents = techniqueContents;
    }

    @Override
    public Tip tip(Result result) {
        if (result == null) return new EmptyTip();
        if (result.passwordIsStrong()) {
            int randomIndex = (int) (Math.random()*adviceTitles.length);
            return new Tip(TipType.ADVICE, adviceTitles[randomIndex], adviceContents[randomIndex]);
        }
        else {
            int randomIndex = (int) (Math.random()*techniqueTitles.length);
            return new Tip(TipType.TECHNIQUE, techniqueTitles[randomIndex], techniqueContents[randomIndex]);
        }
    }
}
