package es.jonatantierno.passwordcoach.domain.model.dictionary;

import org.junit.Test;

import rx.Observable;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Here we test the creation of a dictionary from a stream of tweets
 */
public class DictionaryCreationTest {
    @Test
    public void empty(){
        Observable.just("").flatMap(new DictionaryCreation()).count().subscribe((c) -> assertThat(c,is(0)));
        fail("In process");
    }
}
