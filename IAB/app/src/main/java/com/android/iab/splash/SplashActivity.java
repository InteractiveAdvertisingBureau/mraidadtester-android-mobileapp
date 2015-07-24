package com.android.iab.splash;

/**
 * Created by syed on 7/7/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.android.iab.R;
import com.android.iab.welcome.Welcome_Activity;

/**
 * Home SplashActivity for loading layouts resources
 *
 * This activity is used to display splash screen at start
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class SplashActivity extends Activity {

    // Splash screen timer

    private static int SPLASH_TIME_OUT = 4000;  //Timer to display splash screen
    private Handler splashHandler = new Handler();
    private Runnable r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);


        splashHandler.postDelayed(new Runnable() {

            /*  Showing splash screen with a timer.     */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, Welcome_Activity.class);

                splashHandler.postDelayed(r, SPLASH_TIME_OUT);

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }


}
