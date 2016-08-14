package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryRuleTest {
    @Mock
    WordCheck check;

    @Test
    public void strongPassword() {
        String password = "password";
        when(check.analyze(anyString(), eq(password))).thenReturn(ResultCode.STRONG);

        Result result = new DictionaryRule(listOfWords().iterator(), check).analyze(password);

        for (String word : listOfWords()) {
            verify(check).analyze(word, password);
        }

        assertThat(result, is(new StrongPasswordResult()));
    }

    private List<String> listOfWords() {
        return asList("anyWord", "anotherWord", "aThirdOne");
    }

    @Test
    public void weakPassword() {
        String firstWord = "first";
        String password = "password";
        String third = "third";

        when(check.analyze(firstWord, password)).thenReturn(ResultCode.STRONG);
        when(check.analyze(password, password)).thenReturn(ResultCode.WEAK);

        Result result = new DictionaryRule(asList(firstWord, password, third).iterator(), check)
                .analyze(password);

        verify(check).analyze(firstWord, password);
        verify(check).analyze(password, password);
        verify(check, never()).analyze(password, third);

        assertThat(result, is(new WeakPasswordResult()));
    }

    @Test(expected = IllegalStateException.class)
    public void callTwice() {
        String password = "hola";

        DictionaryRule dictionaryRule = new DictionaryRule(asList(password).iterator());
        dictionaryRule.analyze("3k5jelis ");
        dictionaryRule.analyze("Hola");
    }
}