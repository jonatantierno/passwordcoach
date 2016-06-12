package es.jonatantierno.passwordcoach.domain.model.rules;

public class ShortPasswordRule implements Rule {

    private final int minLength;

    public ShortPasswordRule(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public Result analyze(String password) {
        if (password.length() > minLength) {
            return new StrongPasswordResult();
        }
        else {
            return new WeakPasswordResult(ResultCode.TOO_SHORT);
        }
    }
}
