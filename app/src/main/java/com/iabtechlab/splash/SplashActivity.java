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

package com.iabtechlab.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.iabtechlab.R;
import com.iabtechlab.utility.HelperMethods;
import com.iabtechlab.utility.IntentKey;
import com.iabtechlab.welcome.WelcomeActivity;

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
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 10;
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
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                HelperMethods.showToast_L("Write Permission is needed to perform the application work efficiently", this);
            } /*else {
                if (!SharePref.getWritePermission(getApplicationContext())) {
                    new AlertDialog.Builder(this).setMessage("You need to enable Write permissions to use this feature").setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // navigate to settings
                            Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + SplashActivity.this.getPackageName()));
                            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                            myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SplashActivity.this.startActivity(myAppSettings);
                        }
                    }).setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // leave?
                            SplashActivity.this.onBackPressed();
                        }
                    }).show();
                }
            }*/
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

            // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {

            moveToNextScreen();
        }
    }

    private void moveToNextScreen() {
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
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.iabtechlab";
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
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.iabtechlab";
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                       // SharePref.setWritePermission(false, SplashActivity.this);
                    }
                    moveToNextScreen();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
