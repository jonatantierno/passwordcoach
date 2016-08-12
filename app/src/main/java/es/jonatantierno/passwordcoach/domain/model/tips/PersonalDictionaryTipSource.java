package es.jonatantierno.passwordcoach.domain.model.tips;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;

public class PersonalDictionaryTipSource implements TipSource{
    private final boolean dictionaryEnabled;
    private final Tip activateDictionaryTip;
    private final TipSource innerTipSource;

    public PersonalDictionaryTipSource(boolean dictionaryEnabled, Tip activateDictionaryTip, TipSource innerTipSource) {
        this.dictionaryEnabled = dictionaryEnabled;
        this.activateDictionaryTip = activateDictionaryTip;
        this.innerTipSource = innerTipSource;
    }

    @Override
    public Tip tip(Result result) {
        if (result.passwordIsStrong() && !dictionaryEnabled) {
            return activateDictionaryTip;
        }
        return innerTipSource.tip(result);
    }
}
