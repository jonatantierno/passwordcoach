package es.jonatantierno.passwordcoach.domain.model.tips;

import android.support.annotation.NonNull;

public class SimpleTipSourceContractTest extends TipSourceContractTest {
    @NonNull
    @Override
    public SimpleTipSource buildSource() {
        return new SimpleTipSource();
    }
}
