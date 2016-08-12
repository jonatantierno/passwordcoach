package es.jonatantierno.passwordcoach.domain.model.tips;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.jonatantierno.passwordcoach.domain.model.rules.StrongPasswordResult;
import es.jonatantierno.passwordcoach.domain.model.rules.WeakPasswordResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetATipWithDictionaryDeactivatedTest {
    @Mock
    private TipSource innerTipSource;


    @Test
    public void strongResult() {
        Tip activateDictionaryTip = new Tip(TipType.ACTIVATE_PERSONAL_DICTIONARY, "a", "b");
        Tip tip = new PersonalDictionaryTipSource(false, activateDictionaryTip, innerTipSource).tip(new StrongPasswordResult());

        assertThat(tip, is(activateDictionaryTip));
    }

    @Test
    public void weakResult() {
        Tip innerTip = new Tip(TipType.TECHNIQUE, "", "");
        WeakPasswordResult result = new WeakPasswordResult();
        when(innerTipSource.tip(result)).thenReturn(innerTip);

        Tip tip = new PersonalDictionaryTipSource(false, mock(Tip.class), innerTipSource).tip(result);

        assertThat(tip, is(innerTip));
    }



}