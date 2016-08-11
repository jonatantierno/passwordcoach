package es.jonatantierno.passwordcoach.domain.model.rules;

public class ToggableRule implements Rule{
    private final boolean on;
    private final Rule innerRule;

    public ToggableRule(boolean on, Rule innerRule) {
        this.on = on;
        this.innerRule = innerRule;
    }

    @Override
    public Result analyze(String password) {
        if (on) return innerRule.analyze(password);
        else return new StrongPasswordResult();
    }
}
