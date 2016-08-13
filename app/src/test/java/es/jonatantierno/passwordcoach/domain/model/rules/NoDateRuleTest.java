package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NoDateRuleTest {
    @Test
    public void noYear(){
        Result result = new NoDateRule().analyze("password");

        assertThat(result, is(new StrongPasswordResult()));
    }

    @Test
    public void possibleYear() {
        Result result = new NoDateRule().analyze("summer1980");

        assertThat(result, is(new ContainsPossibleDate()));
    }

    @Test
    public void possibleFullDate() {
        Result result = new NoDateRule().analyze("xx05121980**");

        assertThat(result, is(new ContainsPossibleDate()));
    }

    @Test
    public void possibleHalfDate() {
        Result result = new NoDateRule().analyze("xx121980**");

        assertThat(result, is(new ContainsPossibleDate()));
    }

    @Test
    public void longNumber() {
        Result result = new NoDateRule().analyze("xx120519808**");

        assertThat(result, is(new StrongPasswordResult()));
    }

    @Test
    public void sevenDigits() {
        Result result = new NoDateRule().analyze("xx1234567**");

        assertThat(result, is(new StrongPasswordResult()));
    }
    @Test
    public void fiveDigits() {
        Result result = new NoDateRule().analyze("xx12345**");

        assertThat(result, is(new StrongPasswordResult()));
    }
}