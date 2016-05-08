package com.example.jonatan.passwordcoach.rules;

import java.util.List;

public class SetOfRules implements Rule {
    public SetOfRules(List<Rule> list) {
    }

    @Override
    public Result analyze(String password) {
        return new WeakPasswordResult();
    }
}
