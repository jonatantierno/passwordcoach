package es.jonatantierno.passwordcoach.domain.model.rules;

public class StrongPasswordResult extends BasicResult {

    public StrongPasswordResult() {
        super(ResultCode.STRONG, true);
    }
}
