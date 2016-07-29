package es.jonatantierno.passwordcoach;

import android.support.v4.app.FragmentActivity;

import com.wuman.android.auth.oauth.OAuthHmacCredential;

import java.io.IOException;

import rx.Observer;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class reads tweets from an account and returns them as strings
 */
public class TweetSource {

    public void recover(final FragmentActivity activity, final Observer<String> observer) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                CredentialFactory credentialFactory = new CredentialFactory();
                credentialFactory.init(activity);
                try {
                    OAuthHmacCredential credential = credentialFactory.getCredential();

                    ConfigurationBuilder cb = new ConfigurationBuilder()
                            .setOAuthConsumerKey(activity.getString(R.string.client_id))
                            .setOAuthConsumerSecret(activity.getString(R.string.client_secret))
                            .setOAuthAccessTokenSecret(credential.getTokenSharedSecret())
                            .setOAuthAccessToken(credential.getAccessToken());

                    QueryResult search = new TwitterFactory(cb.build()).getInstance().search(new Query("@twitterapi"));

                    for(Status status: search.getTweets()){
                        observer.onNext(status.getText());
                    }
                    observer.onCompleted();
                } catch (IOException e) {
                    observer.onError(e);
                } catch (TwitterException e) {
                    observer.onError(e);
                }
            }
        }).start();
    }
}
