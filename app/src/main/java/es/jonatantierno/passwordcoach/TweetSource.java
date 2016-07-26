package es.jonatantierno.passwordcoach;

import android.support.v4.app.FragmentActivity;

import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;

import rx.Observer;

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
                    Credential credential = credentialFactory.getCredential();
                    observer.onNext(credential.getAccessToken());
                    observer.onCompleted();
                } catch (IOException e) {
                    observer.onError(e);
                }
            }
        }).start();
    }
}
