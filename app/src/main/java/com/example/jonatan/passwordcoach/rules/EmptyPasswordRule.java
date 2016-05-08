package com.example.jonatan.passwordcoach.rules;

public class EmptyPasswordRule implements Rule {
    @Override
    public Result analyze(String password) {
        if (password.isEmpty()){
            return new WeakPasswordResult();
        } else {
            return new StrongPasswordResult();
        }

    }
}
