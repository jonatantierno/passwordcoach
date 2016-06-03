package com.example.jonatan.passwordcoach.rules;

import java.util.Arrays;
import java.util.List;

public class SetOfRules implements Rule {
    private final List<? extends Rule> rules;

    public SetOfRules(List<? extends Rule> list) {
        rules = list;
    }

    @Override
    public Result analyze(String password) {
        if(rules.isEmpty()) {
            return new WeakPasswordResult();
        }
        for (Rule rule: rules) {
            Result result = rule.analyze(password);
            if(!result.passwordIsStrong()) {
                return result;
            }
        }
        return new StrongPasswordResult();
    }
}
