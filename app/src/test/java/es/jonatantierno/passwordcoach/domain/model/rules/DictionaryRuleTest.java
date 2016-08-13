package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static es.jonatantierno.passwordcoach.domain.model.rules.ResultCode.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
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