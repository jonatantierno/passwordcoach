package es.jonatantierno.passwordcoach.domain.model.rules;

public interface Rule {
    Result analyze(String password);
}
