package com.example.jonatan.passwordcoach.domain.model.rules;

public class WeakPasswordResult implements Result {

    private final ResultCode resultCode;

    public WeakPasswordResult(ResultCode resultCode) {

        this.resultCode = resultCode;
    }
    public WeakPasswordResult() {
        this(ResultCode.WEAK);
    }

    @Override
    public boolean passwordIsStrong() {
        return false;
    }

    @Override
    public ResultCode code() {
        return resultCode;
    }
}
