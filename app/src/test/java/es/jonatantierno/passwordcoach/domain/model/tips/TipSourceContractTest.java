package es.jonatantierno.passwordcoach.domain.model.tips;

import android.support.annotation.NonNull;

import org.junit.Test;

import es.jonatantierno.passwordcoach.domain.model.rules.StrongPasswordResult;
import es.jonatantierno.passwordcoach.domain.model.rules.WeakPasswordResult;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class TipSourceContractTest {
    @NonNull
    public abstract TipSource buildSource();

    @Test
    public void nullResult() {
        assertThat(buildSource().tip(null), instanceOf(EmptyTip.class));
    }

    @Test
    public void strongPasswordResult() {
        assertThat(buildSource().tip(new StrongPasswordResult()).type, is(TipType.ADVICE));
    }

    @Test
    public void weakPasswordResult() {
        assertThat(buildSource().tip(new WeakPasswordResult()).type, is(TipType.TECHNIQUE));
    }
}
