package es.jonatantierno.passwordcoach.domain.model.tips;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;

public interface TipSource {
    Tip tip(Result result);
}
