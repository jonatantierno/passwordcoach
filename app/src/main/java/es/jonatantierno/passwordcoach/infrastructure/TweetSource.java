package es.jonatantierno.passwordcoach.infrastructure;

import android.support.v4.app.FragmentActivity;

import com.wuman.android.auth.oauth.OAuthHmacCredential;

import java.io.IOException;

import es.jonatantierno.passwordcoach.R;
import rx.Subscriber;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class reads tweets from an account and returns them as an observable
 */
public class TweetSource {

    public void load(FragmentActivity activity, Subscriber<? super String> observer) {
        try {
            Twitter twitter = new TwitterFactory(conf(activity)).getInstance();

            observer.onNext(twitter.getScreenName());
            observer.onNext(twitter.showUser(twitter.getId()).getDescription());
            for (Status status : twitter.getUserTimeline()) {
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
