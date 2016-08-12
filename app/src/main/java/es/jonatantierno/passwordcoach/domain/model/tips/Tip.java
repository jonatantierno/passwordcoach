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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tip tip = (Tip) o;

        if (type != tip.type) return false;
        if (title != null ? !title.equals(tip.title) : tip.title != null) return false;
        return content != null ? content.equals(tip.content) : tip.content == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
