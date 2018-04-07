package h4cirf.com.h4cinternetrelayfood;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

import java.util.concurrent.ExecutionException;

/*
 * Comment your name here, save, commit, and then push to show us all you have this working.
 * Abahu
 */

/**
 * Created by Abahu
 * The entry activity from which we can load all of our assets/navigate to the more functional
 * parts of the app
 */
public class EntryActivity extends AppCompatActivity {
    private TextView outText;
    private Auth0 auth0;
    private String accessToken;
    private String tokenID;
    public static final String EA_ACCESS_TOKEN = "EA_ACCESS_TOKEN";
    public static final String EA_TOKEN_ID = "EA_TOKEN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        outText = findViewById(R.id.loginTextView);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
    }

    public void startMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        // Pass our auth0 information to the new activity so we can validate ourselves and pull
        // information from auth0
        intent.putExtra(EA_ACCESS_TOKEN, accessToken);
        intent.putExtra(EA_TOKEN_ID, tokenID);
        startActivity(intent);
    }

    public void login()
    {
        outText.setText("Not logged in");
        String userInfoURL = String.format("https://%s/userinfo", getString(R.string.com_auth0_domain));
        WebAuthProvider.init(auth0)
                .withScheme("https")
                .withAudience(userInfoURL)
                .withScope("openid email profile")
                .start(EntryActivity.this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                        public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EntryActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accessToken = credentials.getAccessToken();
                                tokenID = credentials.getIdToken();
                                outText.setText("Logged in: " + accessToken);
                                startMain();
                            }
                        });
                    }
                });
    }

    public void myMethod() throws ExecutionException, InterruptedException{
        //Some url endpoint that you may have
        String myUrl = "http://www.google.com";
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        result = getRequest.execute(myUrl).get();
    }
}
