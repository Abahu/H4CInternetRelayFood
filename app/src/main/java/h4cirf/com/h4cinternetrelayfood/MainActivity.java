package h4cirf.com.h4cinternetrelayfood;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Protected to allow fragments to access
    protected String accessToken;
    protected String tokenID;

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

    private HomeFragment homeFragment;
    private PostListFragment postListFragment;

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
    }
}
