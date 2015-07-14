package com.android.iab.welcome;

/**
 * Created by syed on 7/7/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.helper.HelperMethods;
import com.android.iab.login.Login_Activity;
import com.android.iab.main.MainActivity;

/**
 * Welcome_Activity for welcome screen
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */
public class Welcome_Activity extends Activity {

    SharedPreferences mPrefs;    // SharedPreference to fetch value from it.

    Boolean loginStatus ;	//to check whether user is logged in or not.

    String userName="";  // store username
    int flag = 0;   //flag to move to another activity
    // Splash screen timer

    private static int SPLASH_TIME_OUT = 3000;
    private Handler splashHandler = new Handler();
    private Runnable r;

    Intent i;
    Button button_start;
    TextView user_welcome_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_screen);

        mPrefs = getSharedPreferences(HelperMethods.MODE_TYPE, MODE_PRIVATE);

        loginStatus =  mPrefs.getBoolean("isLogin", false);
        userName = mPrefs.getString("userName", "");

        user_welcome_text = (TextView) findViewById(R.id.user_welcome_text);
        button_start = (Button) findViewById(R.id.button_start);


        /**
         * check whether is logged in are not & act acordingly
         */
        if(loginStatus){

            user_welcome_text.setText("Welcome  "+userName);
            user_welcome_text.setVisibility(View.VISIBLE);
            button_start.setVisibility(View.GONE);

            callHandler();
        }


        else{
            button_start.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    i = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(i);



                }
            });
        }



    }




    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    private void callHandler()
    {
		 /*  Showing splash screen with a timer. This method will be executed once the timer is over */
        splashHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Welcome_Activity.this, MainActivity.class);

                splashHandler.postDelayed(r, 3000);

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }









}