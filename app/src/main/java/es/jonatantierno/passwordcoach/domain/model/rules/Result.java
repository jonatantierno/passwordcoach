package es.jonatantierno.passwordcoach.domain.model.rules;

public interface Result {
    boolean passwordIsStrong();
    ResultCode code();
}
