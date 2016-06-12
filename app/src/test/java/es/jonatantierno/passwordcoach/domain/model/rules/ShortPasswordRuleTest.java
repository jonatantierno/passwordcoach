package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShortPasswordRuleTest {
    @Test
    public void shortPassword(){
        Result result = new ShortPasswordRule(8).analyze("Short");

        assertThat(result.passwordIsStrong(), is(false));
        assertThat(result.code(), is(ResultCode.TOO_SHORT));
    }

    @Test
    public void longPassword(){
        assertThat(new ShortPasswordRule(8).analyze("This password is long").passwordIsStrong(), is(true));
    }

}