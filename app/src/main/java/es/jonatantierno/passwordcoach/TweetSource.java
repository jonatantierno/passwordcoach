package es.jonatantierno.passwordcoach;

import android.support.v4.app.FragmentActivity;

import com.wuman.android.auth.oauth.OAuthHmacCredential;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class reads tweets from an account and returns them as an observable
 */
public class TweetSource {
    private long count = 0;

    public Observable<String> asObservable(final FragmentActivity activity) {
        return Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        load(activity, sub);
                    }
                }
        );
    }

    private void load(FragmentActivity activity, Subscriber<? super String> observer) {
        TwitterStream stream = null;
        try {
            count = 0;

            Twitter twitter = new TwitterFactory(conf(activity)).getInstance();
            User user = twitter.showUser(twitter.getScreenName());
            observer.onNext(user.getDescription());

            stream = new TwitterStreamFactory(conf(activity)).getInstance();
            stream.addListener(new TwitterStreamListener(observer, stream, 200));
            stream.sample();
        } catch (IOException e) {
            observer.onError(e);
            if (stream != null) stream.shutdown();
        } catch (TwitterException e) {
            observer.onError(e);
        }
    }


    private Configuration conf(FragmentActivity activity) throws IOException {
        CredentialFactory credentialFactory = new CredentialFactory();
        credentialFactory.init(activity);

        OAuthHmacCredential credential = credentialFactory.getCredential();

        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setOAuthConsumerKey(activity.getString(R.string.client_id))
                .setOAuthConsumerSecret(activity.getString(R.string.client_secret))
                .setOAuthAccessTokenSecret(credential.getTokenSharedSecret())
                .setOAuthAccessToken(credential.getAccessToken());

        return cb.build();
    }
}
