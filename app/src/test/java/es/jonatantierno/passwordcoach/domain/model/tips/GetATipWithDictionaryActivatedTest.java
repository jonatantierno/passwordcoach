package es.jonatantierno.passwordcoach.domain.model.tips;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;
import es.jonatantierno.passwordcoach.domain.model.rules.StrongPasswordResult;
import es.jonatantierno.passwordcoach.domain.model.rules.WeakPasswordResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetATipWithDictionaryActivatedTest {
    @Mock
    private TipSource innerTipSource;

    @Test
    public void strongResult() {
        Tip innerTip = new Tip(TipType.TECHNIQUE, "c", "d");
        Result result = new StrongPasswordResult();
        when(innerTipSource.tip(result)).thenReturn(innerTip);

        Tip tip = new PersonalDictionaryTipSource(true, mock(Tip.class), innerTipSource).tip(result);

        assertThat(tip, is(innerTip));
    }

    @Test
    public void weakResult() {
        Tip innerTip = new Tip(TipType.ADVICE, "a", "b");
        Result result = new WeakPasswordResult();
        when(innerTipSource.tip(result)).thenReturn(innerTip);

        Tip tip = new PersonalDictionaryTipSource(true, mock(Tip.class), innerTipSource).tip(result);

        assertThat(tip, is(innerTip));
    }



}