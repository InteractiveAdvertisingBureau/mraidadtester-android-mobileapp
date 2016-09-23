
/**
 * ****************************************************************************
 * Copyright (c) 2015, Interactive Advertising Bureau
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p/>
 * ****************************************************************************
 */

package com.iab.test.iab.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.iab.test.iab.R;
import com.iab.test.iab.database.DataSource;
import com.iab.test.iab.font.SetFont;
import com.iab.test.iab.login.SignUpActivity;
import com.iab.test.iab.main.MainActivity;
import com.iab.test.iab.utility.ApiList;
import com.iab.test.iab.utility.AsyncTaskListner;
import com.iab.test.iab.utility.GetDataFromServer;
import com.iab.test.iab.utility.GlobalInstance;
import com.iab.test.iab.utility.HelperMessage;
import com.iab.test.iab.utility.HelperMethods;
import com.iab.test.iab.utility.IntentKey;
import com.iab.test.iab.utility.SharePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeActivity extends Activity implements AsyncTaskListner {
    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param button_start                        This is a Button which is used to press get Start Option
     * @param user_welcome_text                   This is a TextView which is used to display Welcome Text On Screen
     */
    private Button button_start;
    private TextView user_welcome_text;

    /**
     * Fields which are used in this Class
     *
     * @param mPrefs                              This is a SharedPreferences which is used to get User Information from Local
     * @param loginStatus                         This is a String to check wheter User is Login or Not
     * @param userName                            This is a String which gets the Username from Local
     * @param intent                              This is an Intent which is used to called another Activity from this Activity
     */

    private Boolean loginStatus;
    private String userName = "";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_screen);
        loginStatus = SharePref.isUserLogin(getApplicationContext());

        user_welcome_text = (TextView) findViewById(R.id.user_welcome_text);
        button_start = (Button) findViewById(R.id.button_start);
        if (loginStatus) {
            userName = SharePref.getUserName(getApplicationContext());
            user_welcome_text.setText(getResources().getString(R.string.welcome) + " " + userName);
            user_welcome_text.setVisibility(View.VISIBLE);
            getAllCreativeFromServer();
            button_start.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(IntentKey.IS_LOAD_USER_CREATIVE, true);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            getDefaultCreativeFromServer();
            button_start.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        new SetFont(this);
    }

    /**
     * This Method  is used to getDefaultCreative List From Sever
     */
    private void getDefaultCreativeFromServer() {
        if (HelperMethods.isNetworkAvailable(this)) {
            String url = ApiList.BASE_URL + ApiList.API_URL_GET_ALL_CREATIVE + SharePref.getDefaultAccessKey();
            GetDataFromServer getDataFromServer = new GetDataFromServer(WelcomeActivity.this);
            getDataFromServer.getResponse(url, ApiList.API_URL_GET_ALL_CREATIVE);
        } else {

            openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, this);

        }
    }

    /**
     * This Method  is used to getAllCreative List From Sever
     */


    private void getAllCreativeFromServer() {
        if (HelperMethods.isNetworkAvailable(this)) {
            String url = ApiList.BASE_URL + ApiList.API_URL_GET_ALL_CREATIVE + SharePref.getDefaultAccessKey();
            GetDataFromServer getDataFromServer = new GetDataFromServer(WelcomeActivity.this);
            getDataFromServer.getResponse(url, ApiList.API_URL_GET_ALL_CREATIVE);
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, this);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Methoed: method for creating Alert
     *
     * @param title    Used for display Dialog Title
     * @param message  Used for display Dialog Message
     * @param activity this is Instance of Current Activity
     */
    public void openAlert(String title, String message, Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getDefaultCreativeFromServer();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    /**
     * Interface Callback: callback method for track API response
     * used for getting & extracting response from the JSON
     *
     * @param result  Response String getting By Server
     * @param apiName Identify Which API is used in this Request
     */
    @Override
    public void onTaskComplete(String result, String apiName, int serverRequest) {
        if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TRUE) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getString("status");
                if (status.equals("true")) {
                    JSONArray creativeJsonArray = jsonObject.getJSONArray("response");
                    DataSource dataSource = new DataSource(getApplicationContext());
                    dataSource.open();
                    String p_id;
                    String p_BannerType;
                    String p_CreativeName;
                    String p_Des;
                    String p_SdkName;
                    String r_id;
                    dataSource.deleteAllPreviousData();
                    for (int position = 0; position < creativeJsonArray.length(); position++) {
                        JSONObject creativeJsonObject = creativeJsonArray.getJSONObject(position);
                        p_id = creativeJsonObject.getString("id");
                        r_id = creativeJsonObject.getString("r_id");
                        p_BannerType = creativeJsonObject.getString("p_BannerType");
                        p_CreativeName = creativeJsonObject.getString("p_CreativeName");
                        String  response_data = creativeJsonObject.getString("p_Des");
                        p_Des = response_data.replace("\\", "");
                        p_SdkName = creativeJsonObject.getString("p_SdkName");
                        dataSource.insertCreativeIntoDb(p_id, p_BannerType, p_CreativeName, p_Des, p_SdkName, r_id);
                    }
                    dataSource.close();
                } else {
                    String response = jsonObject.getString("response");
                    HelperMethods.openAlert(getResources().getString(R.string.app_name), response, this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_ERROR) {
                HelperMethods.serverRequestError(getApplicationContext());
            } else if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TIME_OUT) {
                HelperMethods.serverRequestTimeout(getApplicationContext());
            }
        }
    }
}
