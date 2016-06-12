package es.jonatantierno.passwordcoach.domain.model.rules;

import es.jonatantierno.passwordcoach.domain.ports.PasswordMeter;

public class PasswordMeterRule implements Rule {
    private final PasswordMeter meter;
    private final int threshold;

    public PasswordMeterRule(PasswordMeter meter, int threshold) {

        this.meter = meter;
        this.threshold = threshold;
    }

    @Override
    public Result analyze(String password) {
        int measure = meter.measure(password);

        if (measure < threshold) {
            return new WeakPasswordResult(ResultCode.WEAK_ACCORDING_TO_METER);
        }
        else {
            return new StrongPasswordResult();
        }
    }
}
