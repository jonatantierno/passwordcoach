package es.jonatantierno.passwordcoach.domain.ports;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;

public interface Gui {
    void show(Result result);
}
