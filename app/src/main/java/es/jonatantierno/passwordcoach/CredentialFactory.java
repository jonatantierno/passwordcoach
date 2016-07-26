package es.jonatantierno.passwordcoach;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;
import java.util.Collections;

public class CredentialFactory {
    public static final String OAUTH_USER_ID = "250730737";

    public static final String OAUTH_STORE_NAME = "oauth_store";
    public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_TOKEN_URL = "https://api.twitter.com/oauth/authorize";
    public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";

    private OAuthManager oAuthManager;

    public void init(FragmentActivity context) {
        // setup credential store
        SharedPreferencesCredentialStore credentialStore =
                new SharedPreferencesCredentialStore(context,
                        OAUTH_STORE_NAME, new JacksonFactory());
        // setup authorization flow
        AuthorizationFlow.Builder flowBuilder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(ACCESS_TOKEN_URL),
                new ClientParametersAuthentication(
                        context.getString(R.string.client_id),
                        context.getString(R.string.client_secret)),
                context.getString(R.string.user_id),
                AUTHORIZE_TOKEN_URL)
                .setTemporaryTokenRequestUrl(REQUEST_TOKEN_URL)
                .setCredentialStore(credentialStore);

        flowBuilder.setScopes(Collections.EMPTY_LIST);

        AuthorizationUIController controller =
                new DialogFragmentController(context.getFragmentManager()) {

                    @Override
                    public String getRedirectUri() throws IOException {
                        return "http://localhost/Callback";
                    }

                    @Override
                    public boolean isJavascriptEnabledForWebView() {
                        return true;
                    }

                };
        oAuthManager = new OAuthManager(flowBuilder.build(), controller);
    }

    public Credential getCredential() throws IOException {
        return oAuthManager.authorize10a(OAUTH_USER_ID, null, null).getResult();
    }

    public void deleteCredentials() {
        oAuthManager.deleteCredential(OAUTH_USER_ID,null,null);
    }
}
