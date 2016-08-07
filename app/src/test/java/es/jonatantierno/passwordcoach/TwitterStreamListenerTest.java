package es.jonatantierno.passwordcoach;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Subscriber;
import twitter4j.Status;
import twitter4j.TwitterStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterStreamListenerTest {
    @Mock
    Subscriber<String> observer;
    @Mock
    Status status;
    @Mock
    private TwitterStream stream;

    @Test
    public void happyPerfectTest() {
        String tweet = "hola";
        when(status.getText()).thenReturn(tweet);

        new TwitterStreamListener(observer, null, 100).onStatus(status);

        verify(observer).onNext(tweet);
    }

    @Test
    public void exception() {
        Exception exception = new Exception();

        new TwitterStreamListener(observer, null, 100).onException(exception);

        verify(observer).onError(exception);
    }

    @Test
    public void limitation() {
        new TwitterStreamListener(observer, null, 100).onTrackLimitationNotice(0);

        verify(observer).onError(any(Exception.class));
    }

    @Test
    public void notLastTweet() {
        String tweet = "hola";
        when(status.getText()).thenReturn(tweet);

        new TwitterStreamListener(observer, stream, 20).onStatus(status);

        verifyZeroInteractions(stream);
    }

    @Test
    public void tLastTweet() {
        String tweet = "hola";
        when(status.getText()).thenReturn(tweet);

        new TwitterStreamListener(observer, stream, 1).onStatus(status);

        verify(stream).shutdown();
    }
}