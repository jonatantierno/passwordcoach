package es.jonatantierno.passwordcoach.domain.model.rules;

public class WeakPasswordResult extends BasicResult{
    public WeakPasswordResult(ResultCode resultCode) {
        super(resultCode,false);
    }
    public WeakPasswordResult() {
        this(ResultCode.WEAK);
    }
}
