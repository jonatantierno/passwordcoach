package com.example.jonatan.passwordcoach.rules;

public interface Result {
    boolean passwordIsStrong();
    ResultCode code();
}
