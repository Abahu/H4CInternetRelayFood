package h4cirf.com.h4cinternetrelayfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

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
    // TESTING :D

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
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
