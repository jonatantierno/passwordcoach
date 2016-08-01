package es.jonatantierno.passwordcoach;

import android.support.v4.app.FragmentActivity;

import com.wuman.android.auth.oauth.OAuthHmacCredential;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class reads tweets from an account and returns them as strings
 */
public class TweetSource {

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
        try {
            QueryResult search = new TwitterFactory(conf(activity)).getInstance().search(new Query("@twitterapi"));

            for (Status status : search.getTweets()) {
                observer.onNext(status.getText());
            }
            observer.onCompleted();
        } catch (IOException e) {
            observer.onError(e);
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
