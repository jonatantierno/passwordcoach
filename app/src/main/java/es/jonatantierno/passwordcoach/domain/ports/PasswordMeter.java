package es.jonatantierno.passwordcoach.domain.ports;

/**
 * This interface represents a password meter, that returns the score of a password.
 * We will implement this with zxcvbn4j.
 */
public interface PasswordMeter {
    int measure(String password);
}
