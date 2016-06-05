package com.example.jonatan.passwordcoach.domain.model.rules;

public class WeakPasswordResult implements Result {

    @Override
    public boolean passwordIsStrong() {
        return false;
    }

    @Override
    public ResultCode code() {
        return ResultCode.WEAK;
    }
}
