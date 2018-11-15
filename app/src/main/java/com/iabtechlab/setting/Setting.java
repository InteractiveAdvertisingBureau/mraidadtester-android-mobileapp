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

package com.iabtechlab.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iabtechlab.R;
import com.iabtechlab.font.SetFont;
import com.iabtechlab.utility.GlobalInstance;
import com.iabtechlab.utility.HelperMethods;
import com.iabtechlab.utility.SharePref;

/**
 * This activity is used to display User Profile
 */
public class Setting extends Activity implements View.OnClickListener {
    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param header_text                          This is a ThextView which is used to know Term & Condition
     * @param user_name_textView                   This is a ThextView which is used to skip This Page
     * @param company_name_textView                This is a ThextView which is used to get User Name
     * @param version_textView                     This is a ThextView which is used to get User Company Name
     * @param policy_textView                      This is a ThextView which is used to get User Email      *
     * @param accesskey_textView                      This is a ThextView which is used to get Access Key      *
     */
    private TextView header_text;
    private TextView user_name_textView;
    private TextView company_name_textView;
    private TextView version_textView;
    private TextView policy_textView;
    private TextView accesskey_textView;

    /**
     * string for dispaly user profile
     *
     * @param user_nameString               This is a String which is used for get User Name
     * @param company_nameString            This is a String which is used for get User Company Name
     */
    private String user_nameString;
    private String company_nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_screen);
        getUIobjects();
        setOnClickEventListner();
        setHeaderTitle();
        getProfileInfo();
        displayProfileInfo();
        new SetFont(this);
    }

    /**
     * Methoed to getProfile Information
     */
    private void getProfileInfo() {
        SharedPreferences mPrefs;
        mPrefs = getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
        user_nameString = mPrefs.getString(SharePref.USER_NAME, "");
        company_nameString = mPrefs.getString(SharePref.USER_COMPANY, "");
    }

    /**
     * Methoed to Display User Information
     */
    private void displayProfileInfo() {
        user_name_textView.setText(user_nameString);
        company_name_textView.setText(company_nameString);
        version_textView.setText(R.string.app_version);
        accesskey_textView.setText(String.format("Key: %s", SharePref.getUserAccessKey(getApplicationContext())));
    }

    /**
     * Methoed is used to Set Header Title
     */
    private void setHeaderTitle() {
        header_text.setText(getResources().getString(R.string.lebel_setting));
    }

    /**
     * Methoed to add Click Event on UI Object
     */
    private void setOnClickEventListner() {
        header_text.setOnClickListener(this);
        policy_textView.setOnClickListener(this);
        accesskey_textView.setOnClickListener(this);
        findViewById(R.id.user_email_textView).setOnClickListener(this);
    }

    /**
     * Methoed to create UI Object from XML Layout
     */
    private void getUIobjects() {
        header_text = findViewById(R.id.header_text);
        policy_textView = findViewById(R.id.policy_textView);
        user_name_textView = findViewById(R.id.user_name_textView);
        company_name_textView = findViewById(R.id.company_name_textView);
        version_textView = findViewById(R.id.version_textView);
        accesskey_textView = findViewById(R.id.accesskey_textView);
        if (SharePref.isUserLogin(getApplicationContext()) && !SharePref.getUserName(getApplicationContext()).equals(GlobalInstance.DEFAULT_GUEST)) {
            accesskey_textView.setVisibility(View.VISIBLE);
            findViewById(R.id.user_email_textView).setVisibility(View.VISIBLE);
        } else {
            accesskey_textView.setVisibility(View.GONE);
            findViewById(R.id.user_email_textView).setVisibility(View.GONE);
        }
    }

    /**
     * Interface called after Click Event on UI Object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Back button clicked on Header to go Back
            case R.id.header_text:
                finish();
                break;
            //Term & Condition  clicked  to see Policy Using WebView
            case R.id.policy_textView:
                Intent intent = new Intent(this, TermAndCondition.class);
                startActivity(intent);
                break;
            //Its used to share Access Key
            case R.id.user_email_textView:
                String emailContent = GlobalInstance.EMAIL_CONTENT_FOR_ACCESS_KEY;
                emailContent = emailContent.replace(GlobalInstance.ACEESS_KEY, SharePref.getUserAccessKey(getApplicationContext()));
                HelperMethods.sendEmail(this, "", GlobalInstance.SUBJECT_ACEESS_KEY, emailContent);
                break;
        }
    }
}
