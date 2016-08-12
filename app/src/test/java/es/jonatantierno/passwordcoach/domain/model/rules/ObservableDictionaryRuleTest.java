package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ObservableDictionaryRuleTest {
    @Test(expected = RuntimeException.class)
    public void error(){
        new ObservableDictionaryRule(Observable.error(new Exception())).analyze("foo");
    }

    @Test
    public void empty(){
        Result result = new ObservableDictionaryRule(Observable.empty()).analyze("foo");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void wordNotInDictionary(){
        Result result = new ObservableDictionaryRule(Observable.just("password")).analyze("foo");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void containsWordInDictionary() {
        Result result = new ObservableDictionaryRule(Observable.just("bar")).analyze("barco");

        assertThat(result.passwordIsStrong(), is(false));
    }

    @Test
    public void wordInDictionary(){
        Result result = new ObservableDictionaryRule(Observable.just("bar","foo")).analyze("foo");

        assertThat(result.passwordIsStrong(), is(false));
    }
}
