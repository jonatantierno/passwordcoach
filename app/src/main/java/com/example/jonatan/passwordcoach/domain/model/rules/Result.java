package com.example.jonatan.passwordcoach.domain.model.rules;

public interface Result {
    boolean passwordIsStrong();
    ResultCode code();
}
