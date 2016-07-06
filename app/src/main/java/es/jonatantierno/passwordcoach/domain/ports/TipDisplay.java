package es.jonatantierno.passwordcoach.domain.ports;

import es.jonatantierno.passwordcoach.domain.model.tips.Tip;

public interface TipDisplay {
    void show(Tip tip);
}
