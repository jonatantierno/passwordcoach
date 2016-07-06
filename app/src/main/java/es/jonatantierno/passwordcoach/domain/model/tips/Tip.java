package es.jonatantierno.passwordcoach.domain.model.tips;

/**
 * This class represents a tip for the user. It may be a technique to create easy passwords,
 * advice on how to protect a password, a hint on more functionality of the application, etcetera.
 */
public class Tip {
    public final TipType type;
    public final String title;
    public final String content;

    public Tip(TipType type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }
}
