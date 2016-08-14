package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WordCheckTest {

    @Test
    public void emptyDictionaryWord(){
        assertThat(new WordCheck().analyze("","password"), is(ResultCode.STRONG));
    }

    @Test
    public void noMatch(){
        assertThat(new WordCheck().analyze("dictionary","password"), is(ResultCode.STRONG));
    }

    @Test
    public void wordIsPassword(){
        assertThat(new WordCheck().analyze("word","word"), is(ResultCode.IN_DICTIONARY));
    }

    @Test
    public void wordWithLessThanFourLetters(){
        assertThat(new WordCheck().analyze("wor","wor"), is(ResultCode.STRONG));
    }

    @Test
    public void passwordContainsWord(){
        assertThat(new WordCheck().analyze("word","Password"), is(ResultCode.CONTAINS_WORD_IN_DICTIONARY));
    }

    @Test
    public void specialCharsLiteral() {
        assertThat(new WordCheck().analyze("español","español"), is(ResultCode.IN_DICTIONARY));
    }

    @Test
    public void specialCharsProcessed() {
        assertThat(new WordCheck().analyze("español","espanol"), is(ResultCode.IN_DICTIONARY));
        assertThat(new WordCheck().analyze("áéíóúüç","aeiouuc"), is(ResultCode.IN_DICTIONARY));
    }

}