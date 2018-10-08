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

package com.android.iab.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iab.R;
import com.android.iab.font.SetFont;
import com.android.iab.main.MainActivity;
import com.android.iab.setting.TermAndCondition;
import com.android.iab.utility.ApiList;
import com.android.iab.utility.AsyncTaskListner;
import com.android.iab.utility.GetPostDataFromServer;
import com.android.iab.utility.GlobalInstance;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.android.iab.utility.SharePref;
import com.android.iab.welcome.WelcomeActivity;

import org.json.JSONObject;

/**
 * This is an Activity which is used to Register User
 */
public class SignUpActivity extends Activity implements OnClickListener, AsyncTaskListner {

    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param policy_textView               This is a ThextView which is used to know Term & Condition
     * @param skip_Button                   This is a ThextView which is used to skip This Page
     * @param user_name_editText            This is a ThextView which is used to get User Name
     * @param company_name_editText         This is a ThextView which is used to get User Company Name
     * @param user_email_editText           This is a ThextView which is used to get User Email
     * @param login_Button                  This is a ThextView which is used to know Term & Condition
     */
    private TextView policy_textView;
    private TextView skip_Button;
    private EditText user_name_editText;
    private EditText last_name_editText;
    private EditText company_name_editText;
    private EditText user_email_editText;
    private Button login_Button;

    /**
     * String for storing user credentials
     *
     * @param user_nameString               This is a String which is used for get User Name
     * @param company_nameString            This is a String which is used for get User Company Name
     * @param user_emailString              This is a String which is used for get User Email Address
     */
    private String user_nameString;
    private String last_nameString;
    private String company_nameString;
    private String user_emailString;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //Hiding default app header
        setContentView(R.layout.login);
        getUIobjects();
        addClickListner();
        new SetFont(this);
    }

    /**
     * Methoed to create UI Object from XML Layout
     */
    private void getUIobjects() {
        // TODO Auto-generated method stub
        policy_textView = (TextView) findViewById(R.id.policy_textView);
        skip_Button = (TextView) findViewById(R.id.skip_Button);
        user_name_editText = (EditText) findViewById(R.id.user_name_editText);
        last_name_editText = (EditText) findViewById(R.id.last_name_editText);
        company_name_editText = (EditText) findViewById(R.id.company_name_editText);
        user_email_editText = (EditText) findViewById(R.id.user_email_editText);
        login_Button = (Button) findViewById(R.id.login_Button);
    }

    /**
     * Methoed to add Click Event on UI Object
     */
    private void addClickListner() {
        login_Button.setOnClickListener(this);
        skip_Button.setOnClickListener(this);
        policy_textView.setOnClickListener(this);
    }

    /**
     * Interface called after Click Event on UI Object
     */
    public void onClick(View v) {        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_Button:
                user_nameString = user_name_editText.getText().toString();
                last_nameString = last_name_editText.getText().toString();
                user_emailString = user_email_editText.getText().toString();
                company_nameString = company_name_editText.getText().toString();
                if (validateData()) {
                    userLogin();
                }
                break;
            case R.id.skip_Button:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(IntentKey.IS_LOAD_USER_CREATIVE, false);
                startActivity(intent);
                // close this activity
                finish();
                break;
            case R.id.policy_textView:
                Intent policyIntent1 = new Intent(this, TermAndCondition.class);
                startActivity(policyIntent1);
                break;
            default:
                break;
        }
    }

    /**
     * storeData for further use
     *
     * @param user_name               This is a String which is used for get User Name
     * @param company_name            This is a String which is used for get User Company Name
     * @param user_email              This is a String which is used for get User Email Address
     * **/


    /**
     * Methoed:  Mark for Checking The All Input data is filled
     */
    private Boolean validateData() {
        // TODO Auto-generated method stub
        if (user_nameString.length() == 0) {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_FIRST_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (last_nameString.length() == 0) {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_LAST_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (company_nameString.length() == 0) {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_COMPANY_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_emailString.length() == 0) {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!HelperMethods.isEmailValid(user_emailString)) {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL_VALID,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    /**
     *
     * */
    private void userLogin() {
        String data = "firstname=%s&lastname=%s&email=%s&company=%s&platform=android";
        ;
        //data =  URLUTF8Encoder.encode(user_nameString) + "/" +  URLUTF8Encoder.encode(user_nameString) + "/" +  URLUTF8Encoder.encode(user_emailString) + "/" +  URLUTF8Encoder.encode(company_nameString) + "/android";
        data = String.format(data, user_nameString, last_nameString, user_emailString, company_nameString);
        String url = ApiList.IAB_BASE_URL + ApiList.API_URL_RIGISTRATION;
        /*
        GetDataFromServer getDataFromServer = new GetDataFromServer(SignUpActivity.this);
        getDataFromServer.getResponse(url, ApiList.API_URL_SIGN_UP);*/
        GetPostDataFromServer getPostDataFromServer = new GetPostDataFromServer(this);
        getPostDataFromServer.getResponse(url, data, ApiList.API_URL_RIGISTRATION, false);
        openAlert(getResources().getString(R.string.app_name), user_nameString + ", " + HelperMessage.LOGIN_WELCOME_MESSAGE, this);
    }

    /**
     * Interface Callback: callback method for track API response
     * used for getting & extracting response from the JSONObject
     *
     * @param result  Response String getting By Server
     * @param apiName Identify Which API is used in this Request
     */
    public void onTaskComplete(String result, String apiName, int serverRequest) {
        Log.e(result, apiName);
        if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TRUE) {
            try {
                JSONObject jsonObject_response = new JSONObject(result);
                String response = jsonObject_response.getString("response");

                if (response.equalsIgnoreCase("true")) {
                    String access_key = jsonObject_response.getString("accessKey");
                    SharePref.setUserInfo(getApplicationContext(), user_nameString, user_emailString, company_nameString, access_key);
                    //  openAlert(getResources().getString(R.string.app_name), user_nameString + ", " + HelperMessage.LOGIN_WELCOME_MESSAGE, this);
                } else {
                    //   openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_SERVER_ALERT, this);
                }
            } catch (Exception e) {
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

    /**
     * Methoed: method for creating Alert of Successfull Login
     *
     * @param title    Used for display Dialog Title
     * @param message  Used for display Dialog Message
     * @param activity this is Instance of Current Activity
     */
    private void openAlert(String title, String message, Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent signIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                signIntent.putExtra(IntentKey.IS_LOAD_DEFAULT_CREATIVE, false);
                startActivity(signIntent);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
}
