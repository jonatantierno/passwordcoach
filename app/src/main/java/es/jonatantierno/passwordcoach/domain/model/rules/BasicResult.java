package es.jonatantierno.passwordcoach.domain.model.rules;

public class BasicResult implements Result{
    private final ResultCode code;
    private final boolean strong;

    public BasicResult(ResultCode code, boolean strong){
        this.code = code;
        this.strong = strong;
    }
    @Override
    public boolean passwordIsStrong() {
        return strong;
    }

    @Override
    public ResultCode code() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicResult that = (BasicResult) o;

        if (strong != that.strong) return false;
        return code == that.code;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (strong ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicResult{");
        sb.append("code=").append(code);
        sb.append(", strong=").append(strong);
        sb.append('}');
        return sb.toString();
    }
}
