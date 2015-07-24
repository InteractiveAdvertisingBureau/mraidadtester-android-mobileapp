package com.android.iab.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iab.R;
import com.android.iab.helper.HelperMessage;
import com.android.iab.helper.HelperMethods;
import com.android.iab.sqlitehelper.DatabaseHandler;
import com.android.iab.sqlitehelper.User_Details;
import com.android.iab.welcome.Welcome_Activity;


import java.util.List;

/**
 * Login_Activity for loading Ad Tag fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class Login_Activity extends Activity implements OnClickListener {


    LinearLayout back_layout;
    TextView policy_textView,policy_textView2,logged_user_name;
    EditText user_name_editText, company_name_editText, user_email_editText;
    Button login_Button;

    int count = 1; //user count for db

    String user_nameString, company_nameString,user_emailString;   // string for storing user credentials
    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //Hiding default app header
        setContentView(R.layout.login);

        mPrefs = getSharedPreferences(HelperMethods.MODE_TYPE, MODE_PRIVATE);

        findViewById(R.id.back_layout).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();

            }
        });



        getUIobjects();

        addClickListner();

        policy_textView.setMovementMethod(LinkMovementMethod.getInstance());
//		String text = "<a href='http://www.claritusconsulting.com.com'> Terms </a> and " +
//				"<a href='http://www.claritusconsulting.com.com'> Privacy Policy. </a>";
//		policy_textView2.setText(Html.fromHtml(text));
        policy_textView.setLinkTextColor(Color.RED);




    }




    private void getUIobjects() {
        // TODO Auto-generated method stub

        policy_textView2 = (TextView) findViewById(R.id.policy_textView2);
        policy_textView = (TextView) findViewById(R.id.policy_textView);
        logged_user_name = (TextView) findViewById(R.id.logged_user_name);

        user_name_editText = (EditText) findViewById(R.id.user_name_editText);
        company_name_editText = (EditText) findViewById(R.id.company_name_editText);
        user_email_editText = (EditText) findViewById(R.id.user_email_editText);

        login_Button =  (Button) findViewById(R.id.login_Button);

    }



    private void addClickListner() {
        // TODO Auto-generated method stub

        //	policy_textView.setOnClickListener(this);
        login_Button.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.login_Button:

                user_nameString = user_name_editText.getText().toString();
                user_emailString = user_email_editText.getText().toString();
                company_nameString = company_name_editText.getText().toString();

                if(validateData())
                {

                    storeData(count, user_nameString,user_emailString,company_nameString);
                    count++;

                    Editor editor = mPrefs.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("userName", user_nameString);
                    editor.commit();

                    //moving to Main Activity when data is validated
                    Intent intent = new Intent(getApplicationContext(), Welcome_Activity.class);

                    startActivity(intent);
                    // close this activity
                    finish();

                }

                break;

            default:
                break;
        }
    }


    /**
     * storeData for further use
     */
    private void storeData(int count, String user_name, String user_email, String company_name) {
        // TODO Auto-generated method stub

        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting User data

        db.addUser(new User_Details(count, user_name, user_email, company_name ));

        Log.d("Data Inserted: ", " " + count + " " + user_name + " " + user_email + " " + company_name);

        // Reading all contacts
        Log.d("Reading: ", "Reading all data..");
        List<User_Details> user_Details = db.getAllUser();

        for (User_Details user : user_Details)
        {
            String log = "Id: "+user.getId()+" ,Name: " + user.getName() + " ,Email: " + user.getEmail()
                    +" ,Company: " + user.getCompany();

            // Writing Contacts to log
            Log.d("Name: ", log);

        }
    }




    /**
     * validateData for validating data
     */


    private Boolean validateData() {
        // TODO Auto-generated method stub
        if(user_nameString.length()==0){
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (company_nameString.length()==0) {

            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_COMPANY_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user_emailString.length()==0)
        {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!HelperMethods.isEmailValid(user_emailString))
        {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        else
            return true;
    }


}

