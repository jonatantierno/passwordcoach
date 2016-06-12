package es.jonatantierno.passwordcoach.domain.model.dictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryIteratorTest {

    @Mock
    private LineStream stream;

    @Test
    public void nullString(){
        when(stream.readLine()).thenReturn(null);

        assertThat(new DictionaryIterator(stream).hasNext(),is(false));
        assertNull(new DictionaryIterator(stream).next());
    }

    @Test
    public void string() {
        String line = "Hola";
        when(stream.readLine()).thenReturn(line);

        DictionaryIterator iterator = new DictionaryIterator(stream);

        assertThat(iterator.hasNext(),is(true));
        assertThat(iterator.next(), is(line));
        verify(stream,never()).close();
    }

    @Test
    public void severalStrings() {
        String line1 = "Hi";
        String line2 = "Goodbye";
        when(stream.readLine()).thenReturn(line1).thenReturn(line2).thenReturn(null);

        DictionaryIterator iterator = new DictionaryIterator(stream);

        assertThat(iterator.hasNext(),is(true));
        assertThat(iterator.next(), is(line1));
        assertThat(iterator.hasNext(),is(true));
        assertThat(iterator.next(), is(line2));
        assertThat(iterator.hasNext(),is(false));
    }

    @Test
    public void closing() {
        when(stream.readLine()).thenReturn(null);

        new DictionaryIterator(stream).hasNext();

        verify(stream).close();
    }
}