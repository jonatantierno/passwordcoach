package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static es.jonatantierno.passwordcoach.domain.model.rules.ResultCode.CONTAINS_WORD_IN_DICTIONARY;
import static es.jonatantierno.passwordcoach.domain.model.rules.ResultCode.IN_DICTIONARY;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryRuleTest {

    @Test
    public void wordInDictionary(){
        String password = "password";

        Result result = new DictionaryRule(asList(password).iterator()).analyze(password);

        assertThat(result.passwordIsStrong(), is(false));
        assertThat(result.code(), is(IN_DICTIONARY));
    }

    @Test
    public void containsWordInDictionary(){
        String password = "hola";

        Result result = new DictionaryRule(asList(password).iterator()).analyze("**"+password+"1234");

        assertThat(result.passwordIsStrong(), is(false));
        assertThat(result.code(), is(CONTAINS_WORD_IN_DICTIONARY));
    }

    @Test(expected = IllegalStateException.class)
    public void callTwice(){
        String password = "hola";

        DictionaryRule dictionaryRule = new DictionaryRule(asList(password).iterator());
        dictionaryRule.analyze("3k5jelis ");
        dictionaryRule.analyze("Hola");
    }

    @Test
    public void wordOfLessThanFourLetters(){
        Result result = new DictionaryRule(asList("wor").iterator()).analyze("word");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void wordNotInDictionary(){
        Result result = new DictionaryRule(asList("word").iterator()).analyze("another one");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void emptyDictionary(){
        Result result = new DictionaryRule(EMPTY_LIST.iterator()).analyze("foo");

        assertThat(result.passwordIsStrong(), is(true));
    }
}