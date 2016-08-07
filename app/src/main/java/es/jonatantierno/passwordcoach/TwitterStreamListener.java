package es.jonatantierno.passwordcoach;

import rx.Subscriber;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class TwitterStreamListener implements StatusListener {
    private final Subscriber<? super String> observer;
    private final TwitterStream stream;
    private final int maxTweets;
    private int count = 0;

    public TwitterStreamListener(Subscriber<? super String> observer, TwitterStream stream, int maxTweets) {

        this.observer = observer;
        this.stream = stream;
        this.maxTweets = maxTweets;
    }

    @Override
    public void onStatus(Status status) {
        observer.onNext(status.getText());

        if (++count >= maxTweets) {
            stream.shutdown();
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {
        observer.onError(new Exception("Twitter API Track Limitation Notice"));
    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
        observer.onError(new Exception("Twitter API Stall Warning"));
    }

    @Override
    public void onException(Exception e) {
        observer.onError(e);
    }
}
