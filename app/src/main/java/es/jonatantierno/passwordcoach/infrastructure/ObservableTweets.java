package es.jonatantierno.passwordcoach.infrastructure;

import android.support.v4.app.FragmentActivity;

import rx.Observable;
import rx.Subscriber;

public class ObservableTweets {
    private final FragmentActivity activity;

    public ObservableTweets(final FragmentActivity activity) {

        this.activity = activity;
    }

    public Observable<String> go() {
        return Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        new TweetSource().load(activity, sub);
                    }
                }
        );
    }
}
