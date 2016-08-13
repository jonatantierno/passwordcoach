package es.jonatantierno.passwordcoach.domain.model.rules;

import static java.util.regex.Pattern.matches;

public class NoDateRule implements Rule {
    @Override
    public Result analyze(String password) {
        if (containsDigits(password, 9)) {
            return new StrongPasswordResult();
        } else if (containsDigits(password, 8)) {
            return new ContainsPossibleDate();
        } else if (containsDigits(password, 7)) {
            return new StrongPasswordResult();
        } else if (containsDigits(password, 6)) {
            return new ContainsPossibleDate();
        } else if (containsDigits(password, 5)) {
            return new StrongPasswordResult();
        } else if (containsDigits(password, 4)) {
            return new ContainsPossibleDate();
        }
        return new StrongPasswordResult();
    }

    private boolean containsDigits(String password, int digits) {
        return matches(String.format(".*\\d{%d}.*", digits), password);
    }
}
