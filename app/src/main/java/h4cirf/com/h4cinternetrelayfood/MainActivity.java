package h4cirf.com.h4cinternetrelayfood;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // Protected to allow fragments to access
    protected String accessToken;
    protected String tokenID;
    protected UserProfile userProfile;


    private static final String API_URL = "http://food.dlfsystems.com:10100/";
    private HomeFragment homeFragment;
    private PostListFragment postListFragment;

    public Retrofit retrofit;
    public static APIInterface api;

    Retrofit provideRetrofit(OkHttpClient httpClient)
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(API_URL)
                .client(httpClient)
                .build();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToHome();
                    return true;
                case R.id.navigation_market:
                    switchToPostList();
                    return true;
            }
            return false;
        }
    };

    public void switchToHome()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void switchToPostList()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, postListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        accessToken = intent.getStringExtra(EntryActivity.EA_ACCESS_TOKEN);
        tokenID = intent.getStringExtra(EntryActivity.EA_TOKEN_ID);
        // Set up the User Profile
        userProfile = EntryActivity.userProfile;

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Instantiate our fragments
        homeFragment = HomeFragment.newInstance();
        postListFragment = PostListFragment.newInstance();

        // Add our default fragment, a HomeFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Instantiate our Retrofit API
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        retrofit = provideRetrofit(httpClient);
        api = retrofit.create(APIInterface.class);
    }
}
