package es.jonatantierno.passwordcoach.domain.model.tips;

import android.support.annotation.NonNull;

public class RandomTipSourceContractTest extends TipSourceContractTest {

    @NonNull
    @Override
    public TipSource buildSource() {
        return new RandomTipSource(
                new String[]{
                        "advice title 1"
                },
                new String[]{
                        "advice content 1"
                },
                new String[]{
                        "technique title 1"
                },
                new String[]{
                        "techniqhe title 1"
                }
        );
    }
}