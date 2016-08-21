package es.jonatantierno.passwordcoach.infrastructure;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.wuman.android.auth.oauth.OAuthHmacCredential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.jonatantierno.passwordcoach.R;
import rx.Observable;
import rx.Subscriber;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
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
            emitTimeline(observer, twitter);
            emitFriends(observer, twitter);
            observer.onCompleted();
        } catch (IOException e) {
            observer.onError(e);
        } catch (TwitterException e) {
            observer.onError(e);
        } catch (IllegalStateException e) {
            observer.onError(e);
        }
    }

    private void emitFriends(Subscriber<? super String> observer, Twitter twitter) throws TwitterException {
        long cursor= -1;
        PagableResponseList<User> friendsList;
        do {
            friendsList = twitter.getFriendsList(twitter.getId(), cursor);

            for (User friend : friendsList) {
                observer.onNext(friend.getScreenName());
            }
            cursor = friendsList.getNextCursor();
        } while(friendsList.hasNext());
    }

    private void emitTimeline(Subscriber<? super String> observer, Twitter twitter) throws TwitterException {
        observer.onNext(twitter.showUser(twitter.getId()).getDescription());
        for (Status status : twitter.getUserTimeline()) {
            observer.onNext(status.getText());
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
