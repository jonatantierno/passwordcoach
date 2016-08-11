package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToggableRuleTest {
    @Mock
    private Result innerResult;
    @Mock
    private Rule innerRule;

    @Test
    public void disabled(){
        Result result = new ToggableRule(false, innerRule).analyze("indifferent password");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void enabled(){
        String password = "password";
        when(innerRule.analyze(password)).thenReturn(innerResult);
        Result result = new ToggableRule(true, innerRule).analyze(password);

        assertThat(result, is(innerResult));
    }

}