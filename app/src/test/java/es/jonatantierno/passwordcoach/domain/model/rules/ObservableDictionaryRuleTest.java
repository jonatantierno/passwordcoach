package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

@RunWith(MockitoJUnitRunner.class)
public class ObservableDictionaryRuleTest {
    @Mock
    WordCheck check;

    @Test(expected = RuntimeException.class)
    public void error() {
        new ObservableDictionaryRule(Observable.error(new Exception())).analyze("foo");
    }

    @Test
    public void strongPassword() {
        String password = "password";
        when(check.analyze(anyString(), eq(password))).thenReturn(ResultCode.STRONG);

        Result result = new ObservableDictionaryRule(listOfWords(), check).analyze(password);

        listOfWords().forEach(word -> verify(check).analyze(word, password));

        assertThat(result, is(new StrongPasswordResult()));
    }

    private Observable<String> listOfWords() {
        return just("anyWord", "anotherWord", "aThirdOne");
    }

    @Test
    public void weakPassword() {
        String firstWord = "first";
        String password = "password";
        String third = "third";

        when(check.analyze(firstWord, password)).thenReturn(ResultCode.STRONG);
        when(check.analyze(password, password)).thenReturn(ResultCode.WEAK);

        Result result = new ObservableDictionaryRule(just(firstWord, password, third), check)
                .analyze(password);

        verify(check).analyze(firstWord, password);
        verify(check).analyze(password, password);
        verify(check, never()).analyze(password, third);

        assertThat(result, is(new WeakPasswordResult(ResultCode.IN_PERSONAL_ATTACK_DICTIONARY)));
    }
}
