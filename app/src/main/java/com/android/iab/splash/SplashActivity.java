/**
 * ****************************************************************************
 * Copyright (c) 2015, Interactive Advertising Bureau
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * ****************************************************************************
 */

package com.android.iab.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;

import com.android.iab.R;
import com.android.iab.utility.IntentKey;
import com.android.iab.welcome.WelcomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Home SplashActivity for loading layouts resources
 * This activity is used to display splash screen at start
 */
public class SplashActivity extends Activity {
    /**
     * @param SPLASH_TIME_OUT                          This is a Timer to display splash screen
     * @param splashHandler                            This is a Handler which is called Runnable Interface
     */
    private static int SPLASH_TIME_OUT = 2000;  //Timer to display splash screen
    private Handler splashHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkDataBase();
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                i.putExtra(IntentKey.IS_LOAD_DEFAULT_CREATIVE, true);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    /**
     * Check whether Sqlite Database  is Already created or need to Create
     */
    private void checkDataBase() {
        /** Database information*/
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.android.iab";
        File direct = new File(path);
        if (!direct.exists()) {
            if (direct.mkdirs()) {
                //directory is created;
            }
            //to copy db from assets to app db
            try {
                copyDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Methoed to Copy Data from Assets to corresponding path which is given in this methoed
     */
    private void copyDataBase() throws IOException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.android.iab";
        String DB_NAME = "script.db";
        try {
            // Open your local db as the input stream
            InputStream myInput = getAssets().open(DB_NAME);
            // Path to the just created empty db
            String outFileName = path + "/" + DB_NAME;
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
