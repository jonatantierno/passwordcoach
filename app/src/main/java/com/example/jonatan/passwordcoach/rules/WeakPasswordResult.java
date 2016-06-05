package com.example.jonatan.passwordcoach.rules;

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
