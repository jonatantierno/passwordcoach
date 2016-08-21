package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShortPasswordRuleTest {
    @Test
    public void shortPassword(){
        Result result = new ShortPasswordRule(8).analyze("Short");

        assertThat(result, is(new WeakPasswordResult(ResultCode.TOO_SHORT)));
    }

    @Test
    public void almostRight(){
        Result result = new ShortPasswordRule(8).analyze("ln=sevn");

        assertThat(result, is(new WeakPasswordResult(ResultCode.TOO_SHORT)));
    }

    @Test
    public void justRight(){
        Result result = new ShortPasswordRule(8).analyze("ln=eight");

        assertThat(result, is(new StrongPasswordResult()));
    }

    @Test
    public void longPassword(){
        Result result = new ShortPasswordRule(8).analyze("This password is long");
        
        assertThat(result, is(new StrongPasswordResult()));
    }

}